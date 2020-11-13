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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.resource.Resource;

/**
 * An Iteration contains all the informations relative to a single iteration
 * about a single device. It is related to one of them. It lists all the
 * {@link Action} that this device did this time.
 * 
 * @author Martin Heywang
 */
public final class Iteration {

	/**
	 * The list of actions.
	 */
	private final List<Action> actions = new ArrayList<>();

	/**
	 * The device that this iteration belong to.
	 */
	private final Device device;

	/**
	 * Creates a new Iteration with the given device.
	 * 
	 * @param device
	 *            the device
	 */
	public Iteration(Device device) {
		this.device = device;
	}

	/**
	 * Returns an unmodifable version of the list of actions.
	 * 
	 * @return the list of all actions
	 */
	public List<Action> getActions() {
		// Make it unmodifiable
		return Collections.unmodifiableList(this.actions);
	}

	/**
	 * Returns the last action that this device did in this Iteration.
	 * 
	 * @return the last action
	 */
	public Action getLastAction() {
		if (this.actions.isEmpty())
			return null;
		// Get the last element
		return this.actions.get(this.actions.size() - 1);
	}

	/**
	 * Returns how many times the device was activated during this iteration.
	 * 
	 * @return the number of action
	 */
	public Integer getActCount() {
		return this.actions.size();
	}

	/**
	 * Returns the device that this iteration belong to, or in other word the
	 * executor of the actions.
	 * 
	 * @return the device
	 */
	public Device getDevice() {
		return this.device;
	}

	/**
	 * Calculates and returns the cost of this iteration, by doing the summation
	 * of the cost of all the actions.
	 * 
	 * @return the total cost of this iteration
	 */
	public BigInteger getCost() {
		BigInteger amount = BigInteger.ZERO;

		for (final Action action : this.actions)
			amount = amount.add(action.getCost());
		return amount;
	}

	/**
	 * Returns all the resources that the executor received during this
	 * iteration.
	 * 
	 * @return the received resources
	 */
	public List<Pack> getReceivedResources() {
		final List<Pack> packs = new ArrayList<>();

		for (final Action action : this.actions) {
			final Pack receivedPack = action.getReceivedPack();
			if (receivedPack == null)
				continue;
			final Resource resource = receivedPack.getResource();
			boolean added = false;

			for (final Pack pack : packs)
				if (pack.getResource().equals(resource)) {
					pack.addQuantity(receivedPack.getQuantity());
					added = true;
					break;
				}
			if (!added)
				packs.add(receivedPack);
		}

		return packs;
	}

	/**
	 * Returns all the resources that the executor gave during this iteration.
	 * 
	 * @return the given resources
	 */
	public List<Pack> getGivenResources() {
		final List<Pack> packs = new ArrayList<>();

		for (final Action action : this.actions) {
			final Pack givenPack = action.getGivenPack();
			if (givenPack == null)
				continue;
			final Resource resource = givenPack.getResource();
			boolean added = false;

			for (final Pack pack : packs)
				if (pack.getResource().equals(resource)) {
					pack.addQuantity(givenPack.getQuantity());
					added = true;
					break;
				}
			if (!added)
				packs.add(givenPack);
		}

		return packs;
	}

	/**
	 * Returns the time of this iteration, when it began <em>for this
	 * device</em>.
	 * 
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return this.actions.get(0).getTime();
	}

	/**
	 * Returns the last use time, in other word, the time when the last action
	 * began. If there are no actions, returns a time corresponding to 10
	 * seconds before now.
	 * 
	 * @return the last time
	 */
	public LocalDateTime getLastTime() {
		final Action lastAction = this.getLastAction();
		if (lastAction == null)
			// Like a random value
			return LocalDateTime.now().minusSeconds(10);

		return lastAction.getTime();
	}

	/**
	 * <p>
	 * Add the given action to the list of actions of this array.
	 * </p>
	 * <p>
	 * If the executor of the action is either null or not the same as
	 * {@link #device}, this method will return without doing anything.
	 * </p>
	 * 
	 * @param action
	 *            the action to add
	 */
	public void addAction(Action action) {
		if (action.getExecutor() == null || action.getExecutor() != this.device)
			return;

		this.actions.add(action);
	}

}
