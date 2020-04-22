package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.martin.Database;
import com.martin.model.Packing;
import com.martin.model.Resource;
import com.martin.model.Stock;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.model.appareils.comportement.Conveyor_;
import com.martin.model.exceptions.MoneyException;
import com.martin.view.JeuContrôle;

public class Sorter extends Device {

	private Packing crit1, crit2;

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.EXIT,
			PointerTypes.EXIT);

	public Sorter(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		try {
			// Query for all the packages that are associated to this device
			final List<Packing> list = Database.daoPacking().queryBuilder()
					.where().eq("device", model.getIdAppareilModel()).query();
			// If its size equals 0, then create the resource and save it in the
			// database
			if (list.size() == 0) {
				crit1 = new Packing(Resource.NONE, 1, model);
				Database.daoPacking().create(crit1);
				crit2 = new Packing(Resource.NONE, 1, model);
				Database.daoPacking().create(crit2);
			}
			// Else we get at the first index the packing
			else {
				crit1 = list.get(0);
				crit2 = list.get(1);

			}

			// If the list is bigger than 2, there is an error (the resource was
			// added by the user (not in game)).
			// So the rest just doesn't matter
		} catch (SQLException e) {
			System.err.println(e.getLocalizedMessage());

		}

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
		behaviour = new Conveyor_(model, controller);

	}

	@Override
	public void action(Stock resATraiter) throws MoneyException {
		// Check for criterias to give the good pointer
		// First case (crit1), resource goes to the left
		if (resATraiter.get(0).getRessource().equals(crit1.getRessource())) {
			behaviour.action(resATraiter,
					template.getPointersFor(PointerTypes.EXIT).get(2));
			// Index 2 corresponds to the left because it should be the last
			// element (we rotate clockwise)
		}
		// Second case (crit2), the resource goes to the right
		else if (resATraiter.get(0).getRessource()
				.equals(crit2.getRessource())) {
			behaviour.action(resATraiter,
					template.getPointersFor(PointerTypes.EXIT).get(0));
			// Index 0 corresponds to the first element
		}
		// And dead in the center if there are no matching.
		else {
			behaviour.action(resATraiter,
					template.getPointersFor(PointerTypes.EXIT).get(1));
			// Index 1 is the center
		}
	}

	public void setCritère1(Resource res) {
		this.crit1 = new Packing(res, 1);
	}

	public void setCritère2(Resource res) {
		this.crit2 = new Packing(res, 1);
	}

	public Resource getCritère1() {
		return crit1.getRessource();
	}

	public Resource getCritère2() {
		return crit2.getRessource();
	}
}