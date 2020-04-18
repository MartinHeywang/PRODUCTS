package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.Stock;
import com.martin.model.appareils.comportement.Buyer_;
import com.martin.model.appareils.orientation.Exits;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Buyer extends Device {

	public static ArrayList<Coordinates> liste = new ArrayList<Coordinates>();

	public Buyer(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {

		super(model, controller);
		liste.add(model.getCoordinates());

		for (Direction exit : Exits.listForCenter(model.getDirection())) {
			exitsPointers.add(new Coordinates(model.getCoordinates(), exit));
		}

		// Todo: test behaviour
		behaviour = new Buyer_(model, controller);
	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {
		System.out.println("action(Stock) in type Buyer");
		// We save the pointed device (here this device's type has only one
		// exit)
		// The rest of the list doesn't matter (it should count one element
		// only)
		final Device pointedDevice = controller
				.findDevice(exitsPointers.get(0));
		// Then we check if the pointed device points also this device, in
		// this case, there is a connection
		// The action can be launched.
		if (pointedDevice.getEntriesPointers()
				.contains(model.getCoordinates())) {
			// We launch the action with the current pointer
			behaviour.action(resATraiter, exitsPointers.get(0));
		}
		// Here, if there isn't a connection, nothing gets launched (so the
		// game is less laggy).
	}

	/**
	 * 
	 * @return the distributed resource
	 * @throws NullPointerException if the behaviour of this device isn't
	 *                              a buyer
	 */
	public Resource getRessourceDistribuée() throws NullPointerException {
		if (behaviour instanceof Buyer_)
			return ((Buyer_) behaviour)
					.getRessourceDistribuée().getRessource();
		return null;
	}

	/**
	 * 
	 * @param res the new value of the property
	 */
	public void setRessourceDistribuée(Resource res) {
		if (behaviour instanceof Buyer_) {
			((Buyer_) behaviour)
					.setRessourceDistribuée(new Packing(res, 1));
		}
	}
}