package com.martinheywang.products.model.devices;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.toolbox.LocatedHashMap;

/**
 * <p>
 * An IterationReport's instance stores all the data about an iteration of the
 * game loop for a particular device, such as the action count, the price of all
 * of these...
 * </p>
 * <p>
 * These informations are used for limitations purposes, and should be stayed up
 * to date. It also serves the UI to display some cool stuff to the user.
 * </p>
 */
public final class IterationReport {

    /**
     * How many time this device was activated during this iteration.
     */
    private int actCount;

    /**
     * When this iteration began.
     */
    private LocalDateTime beginTime;

    /**
     * When the last action of this device began.
     */
    private LocalDateTime lastUseTime;

    /**
     * The total cost of this device during the iteration.
     */
    private BigInteger totalCost;

    /**
     * The device that makes the report.
     */
    private Device device;

    /**
     * A Map representing what the device received, from where, during the
     * iteration.
     */
    private LocatedHashMap<Coordinate, Pack> received;

    /**
     * A Map representing what the device gave, to where, during the iteration.
     */
    private LocatedHashMap<Coordinate, Pack> given;

    /**
     * Creates a new IterationReport with default values.
     */
    public IterationReport(Device device) {
        this.actCount = 0;
        this.beginTime = LocalDateTime.now();
        this.lastUseTime = LocalDateTime.now();
        this.totalCost = BigInteger.ZERO;
        this.received = new LocatedHashMap<>();
        this.given = new LocatedHashMap<>();

        this.device = device;
    }

    /**
     * 
     * @return the act count
     */
    public int getActCount() {
        return this.actCount;
    }

    /**
     * Calculates, then returns the max act count. It corresponds to the level value
     * times the entries count times two.
     * 
     * @return the max use of this device.
     */
    public int getMaxActCount() {
        return device.getLevel().getValue() * device.getEntriesCount() * 2;
    }

    /**
     * 
     * @return the begin time
     */
    public LocalDateTime getBeginTime() {
        return this.beginTime;
    }

    /**
     * 
     * @return the total cost
     */
    public BigInteger getTotalCost() {
        return this.totalCost;
    }

    /**
     * Returns the whole map that stores the received packs.
     */
    public LocatedHashMap<Coordinate, Pack> getAllReceived() {
        return received;
    }

    /**
     * Returns the whole map that stores the given packs.
     */
    public LocatedHashMap<Coordinate, Pack> getAllGiven() {
        return given;
    }

    /**
     * Returns all the packs received from a particular device.
     * 
     * @param coord the target coordinate
     * @return a list of packs received from the given coordinate
     */
    public List<Pack> getReceivedFrom(Coordinate coord) {
        return this.received.get(coord);
    }

    /**
     * Returns all the packs gived to a particular device.
     * 
     * @param coord the target coordinate
     * @return a list of packs given to the coordinate
     */
    public List<Pack> getGivenTo(Coordinate coord) {
        return this.given.get(coord);
    }

    /**
     * Returns the device that made this report.
     * 
     * @return the device
     */
    public Device getDevice() {
        return this.device;
    }

    /**
     * The date and time that this device began an action for the last time.
     * 
     * @return the last use time
     */
    public LocalDateTime getLastUseTime() {
        return this.lastUseTime;
    }

    /**
     * Increments the act count, meaning that the concerned device just made an
     * action.
     */
    public void incrementActCount() {
        this.actCount++;
    }

    /**
     * Increases the total amount count.
     * 
     * @param amount the amount to add.
     */
    public void increaseTotalCost(BigInteger amount) {
        this.totalCost = this.totalCost.add(amount);
    }

    /**
     * Adds a received pack to the map. If an existing pack with the same resource
     * was already added with the same source, a merge will happen.
     */
    public void addReceivedPack(Coordinate from, Pack pack) {
        /*
         * if (received.containsKey(from)) { final List<Pack> packs =
         * received.get(from); for (Pack item : packs) { if
         * (item.getResource().equals(pack.getResource())) {
         * item.setQuantity(item.getQuantity().add(pack.getQuantity())); return; } } }
         */
        received.put(from, pack);
    }

    /**
     * Adds a given pack to the map. If an existing pack with the same resource and
     * the same target, a merge will happen.
     */
    public void addGivenPack(Coordinate to, Pack pack) {
        /*
         * if (given.containsKey(to)) { final List<Pack> packs = given.get(to); for
         * (Pack item : packs) { if (item.getResource().equals(pack.getResource())) {
         * item.setQuantity(item.getQuantity().add(pack.getQuantity())); return; } } }
         */
        // If such a resource wasn't found yet, just put a clone of it (against pass by
        // reference)
        given.put(to, new Pack(pack.getResource(), pack.getQuantity()));
    }

    public void setLastUseTime(LocalDateTime time) {
        this.lastUseTime = time;
    }
}
