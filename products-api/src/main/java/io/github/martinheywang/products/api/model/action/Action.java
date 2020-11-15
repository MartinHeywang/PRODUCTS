/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.model.action;

import java.math.BigInteger;
import java.time.LocalDateTime;

import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.device.Device;

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
	 * @see io.github.martinheywang.products.api.model.device.Device
	 */
	private final Device executor;

	/**
	 * The position of the executor when this action was performed.
	 * 
	 * @see com.martinheywang.products.api.model.device.Coordinate
	 */
	private final Coordinate position;

	/**
	 * The position of the requested device to be triggered. May be null if the
	 * executor didn't request
	 * 
	 * @see com.martinheywang.products.api.model.device.Coordinate
	 */
	private Coordinate output;

	/**
	 * The pack that was given to the executor before it does anything. May be
	 * null, if the executor is self-sufficient, like the buyers (it has no
	 * entries).
	 */
	private Pack receivedPack;

	/**
	 * The pack that the executor gave to the next device. May be null, for
	 * examples if this action isn't successful.
	 */
	private Pack givenPack;

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
	 * The time of this action, when it began.
	 */
	private final LocalDateTime time;

	/**
	 * Creates a new Action
	 * 
	 * @param executor
	 *            the executor of this action.
	 * @param received
	 *            the resource that the executor received before doing anything
	 */
	public Action(Device executor, Pack received) {
		this.cost = BigInteger.ZERO;
		this.executor = executor;
		this.position = executor.getPosition();
		this.successful = false;
		this.receivedPack = received;
		this.time = LocalDateTime.now();
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
	 * 
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return this.time;
	}

	/**
	 * @return the receivedResources
	 */
	public Pack getReceivedPack() {
		return this.receivedPack;
	}

	/**
	 * @return the givenResources
	 */
	public Pack getGivenPack() {
		return this.givenPack;
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
	 * @param output
	 *            the output to set
	 */
	public void setOutput(Coordinate output) {
		this.output = output;
	}

	/**
	 * @param receivedPack
	 *            the receivedResources to set
	 */
	public void setReceivedPack(Pack receivedPack) {
		if (receivedPack == null)
			this.receivedPack = null;

		this.receivedPack = new Pack(receivedPack.getResource(),
				receivedPack.getQuantity());
	}

	/**
	 * @param givenPack
	 *            the givenPacks to set
	 */
	public void setGivenPack(Pack givenPack) {
		if (givenPack == null)
			this.givenPack = null;

		this.givenPack = new Pack(givenPack.getResource(),
				givenPack.getQuantity());
	}

	/**
	 * Mark this action as successful.
	 */
	public void markAsSuccessful() {
		this.successful = true;
	}

	/**
	 * @param cost
	 *            the amount to add
	 */
	public void addCost(BigInteger cost) {
		this.cost = this.cost.add(cost);
	}

}
