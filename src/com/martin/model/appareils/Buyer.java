package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.appareils.comportement.Buyer_;
import com.martin.model.appareils.orientation.Entrances;
import com.martin.model.appareils.orientation.Exits;
import com.martin.view.JeuContrôle;

public class Buyer extends Device {

	public static ArrayList<Coordinates> liste = new ArrayList<Coordinates>();

	public Buyer(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {

		super(model, controller);
		liste.add(model.getCoordonnees());

		entrances = Entrances.listForNone();
		entrances = Exits.listForCenter(model.getDirection());

		// Todo: add behaviour
	}

	/**
	 * 
	 * @return the ditributed resource
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