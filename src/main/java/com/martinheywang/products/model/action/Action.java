package com.martinheywang.products.model.action;

import java.math.BigInteger;

import com.martinheywang.products.model.Coordinate;
import com.martinheywang.products.model.Pack;
import com.martinheywang.products.model.device.Device;

/**
 * An Action represents an action of a certain device. It contains a lot of
 * information about a precise action.
 * 
 * @author Martin HEYWANG
 */
public final class Action {

    /**
     * The executor of this action, the device that performed this action.
     * 
     * @see com.martinheywang.products.model.device.Device
     */
    private final Device executor;

    /**
     * The position of the executor when this action was performed.
     * 
     * @see com.martinheywang.products.model.Coordinate
     */
    private final Coordinate position;

    /**
     * The position of the requested device to be triggered. May be null if the
     * executor didn't request
     * 
     * @see com.martinheywang.products.model.Coordinate
     */
    private Coordinate output;

    /**
     * The pack that was given to the executor before it does anything. May be null,
     * if the executor is self-sufficient, like the buyers (it has no entries).
     */
    private Pack receivedResources;

    /**
     * The pack that the executor gave to the next device. May be null, for examples
     * if this action isn't successful.
     */
    private Pack givenResources;

    /**
     * Vault <code>true</code> if the executor of this action marked it as
     * successful. Unless <code>false</code>.
     */
    private Boolean successful;

    /**
     * The total cost of this action, how many the executor spent on this acion.
     * 
     * @see java.math.BigInteger
     */
    private BigInteger cost;

    /**
     * Creates a new Action
     * 
     * @param executor the executor of this action.
     * @param received the resource that the executor received before doing anything
     */
    public Action(Device executor, Pack received) {
	this.cost = BigInteger.ZERO;
	this.executor = executor;
	this.position = executor.getPosition();
	this.successful = false;
	this.receivedResources = received;
    }

    /**
     * @return the executor
     */
    public Device getExecutor() {
	return this.executor;
    }

    /**
     * @return the position
     */
    public Coordinate getPosition() {
	return this.position;
    }

    /**
     * @return the output
     */
    public Coordinate getOutput() {
	return this.output;
    }

    /**
     * @return the receivedResources
     */
    public Pack getReceivedResources() {
	return this.receivedResources;
    }

    /**
     * @return the givenResources
     */
    public Pack getGivenResources() {
	return this.givenResources;
    }

    /**
     * @return the successful
     */
    public Boolean isSuccessful() {
	return this.successful;
    }

    /**
     * @return the cost
     */
    public BigInteger getCost() {
	return this.cost;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(Coordinate output) {
	this.output = output;
    }

    /**
     * @param receivedResources the receivedResources to set
     */
    public void setReceivedResources(Pack receivedResources) {
	this.receivedResources = receivedResources;
    }

    /**
     * @param givenResources the givenResources to set
     */
    public void setGivenResources(Pack givenResources) {
	this.givenResources = givenResources;
    }

    /**
     * Mark this action as successful.
     */
    public void markAsSuccessful() {
	this.successful = true;
    }

    /**
     * @param cost the amount to add
     */
    public void addCost(BigInteger cost) {
	this.cost = this.cost.add(cost);
    }

}
