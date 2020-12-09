package io.github.martinheywang.products.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import io.github.martinheywang.products.api.model.exception.MoneyException;

/**
 * The MoneyController is a sub-part of the {@link #GameController} that takes
 * care about the money.
 */
public final class MoneyController {

    private static final int maxRegisteredVariations = 100;
    private static final int offlineMoneyDivider = 4;

    private final GameController controller;

    // The amount given in the constructor
    private BigInteger lockedAmount;
    // The current amount of money
    private BigInteger currentAmount;
    // The amount before the last registered transaction, used to calculate
    // variations.
    private BigInteger lastAmount;

    private final List<BigInteger> registeredVariations;

    /**
     * Creates a new MoneyController.
     * 
     * @param controller the current game controller
     * @param amount     the amount of money to start with, at least 0
     */
    public MoneyController(GameController controller, BigInteger amount) {
        this.controller = controller;
        this.registeredVariations = new ArrayList<>();

        this.currentAmount = amount;
        this.lockedAmount = amount;
        this.lastAmount = amount;
    }

    /**
     * Adds money to the game.
     * 
     * @param value the amount to add
     * @throws MoneyException if the money reaches 0 or less before the transaction.
     */
    public void addMoney(BigInteger value) throws MoneyException {
        // Removes the negation of the value ( - and - becomes +)
        this.removeMoney(value.negate());
    }

    /**
     * Removes money to the game
     * 
     * @param value the amount to remove
     * @throws MoneyException if the money amount reaches 0 or less.
     */
    public void removeMoney(BigInteger value) throws MoneyException {
        final BigInteger projection = currentAmount.subtract(value);
        setMoney(projection);
    }

    private void setMoney(BigInteger value) throws MoneyException {
        if (isNegative(value)) {
            throw new MoneyException(currentAmount, value);
        }
        this.lastAmount = currentAmount;
        this.currentAmount = value;
        controller.refreshMoney();
    }

    private boolean isNegative(BigInteger value) {
        return value.compareTo(BigInteger.ZERO) < 0 ? true : false;
    }

    /**
     * Registers the last change made, as a variation between the last amount,
     * before the transaction, and the current amount. You can't register a change
     * it it older. Those variations will be used when calculating the average
     * variation.
     */
    public void registerChange() {
        registeredVariations.add(currentAmount.subtract(lastAmount));

        // For optimisation purpose
        while (maxRegisteredVariations < registeredVariations.size()) {
            registeredVariations.remove(0);
        }
    }

    /**
     * Clears all the registered variations.
     */
    public void clearVariations() {
        registeredVariations.clear();
    }

    /**
     * Undoes the last transaction and sets the {@link #currentAmount()} to the
     * {@link #lastAmount()}. This action is considered as a transaction itself and
     * will be registerable.
     */
    public void undo() throws MoneyException {
        setMoney(lastAmount);
    }

    /**
     * The purpose of this method is to reset all the transactions (registered or
     * not) to the {@link #lockedAmount()}. This action is considered as a
     * transaction itself and will be registerable.
     */
    public void reset() throws MoneyException {
        clearVariations();
        setMoney(lockedAmount);
    }

    /**
     * Calculates the average variation based on the registered variations.
     * 
     * @return the average variation.
     */
    public BigInteger getAverageVariation() {
        BigInteger average = BigInteger.ZERO;
        for (BigInteger i : registeredVariations) {
            average = average.add(i);
        }
        average = average.divide(BigInteger.valueOf(registeredVariations.size()));
        return average;
    }

    /**
     * Calculates the offline between between the since argument and now
     * 
     * @param since since when
     * @return how many money the player would have earned.
     */
    public BigInteger calculateOfflineMoney(LocalDateTime since) {
        final BigInteger grow = controller.getGame().getGrow();
        final LocalDateTime now = LocalDateTime.now();

        final long millis = ChronoUnit.MILLIS.between(since, now);

        final BigInteger offlineTotal = grow.multiply(BigInteger.valueOf(millis))
                .divide(BigInteger.valueOf(offlineMoneyDivider))
                .divide(BigInteger.valueOf(controller.loopController().getDelay()));
        return offlineTotal;
    }

    /**
     * Returns the current amount of money.
     * 
     * @return the current amount of money.
     */
    public BigInteger currentAmount() {
        return currentAmount;
    }

    /**
     * Returns the last amount of money, the amount before the latest transaction
     * (registered or not).
     * 
     * @return the last amount of money
     */
    public BigInteger lastAmount() {
        return lastAmount;
    }

    /**
     * Returns the locked amount of money, in other words the amount that was passed
     * in the constructor.
     * 
     * @return the locked amount
     */
    public BigInteger lockedAmount() {
        return lockedAmount;
    }
}