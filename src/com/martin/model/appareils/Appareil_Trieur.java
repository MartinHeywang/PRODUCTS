package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Appareil_Trieur extends Appareil {

	private Paquet crit1, crit2;

	public Appareil_Trieur(AppareilModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		// Todo : load the criterias

		entrances = Entrées.listForUp(model.getDirection());
		exits = Sorties.listForNone();

		// Todo : add behaviour
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {

		// FixMe : switch for criterias
		if (resATraiter.get(0).getRessource().equals(crit1.getRessource())) {
			switch (direction) {
			case UP:
				sorties = new Sorties_Right();
				pointerExit = sorties.getPointer(direction);
				break;
			case RIGHT:
				sorties = new Sorties_Center();
				pointerExit = sorties.getPointer(direction);
				break;
			case DOWN:
				sorties = new Sorties_Left();
				pointerExit = sorties.getPointer(direction);
				break;
			case LEFT:
				sorties = new Sorties_Up();
				pointerExit = sorties.getPointer(direction);
				break;
			default:
				break;
			}
		} else if (resATraiter.get(0).getRessource()
				.equals(crit2.getRessource())) {
			switch (direction) {
			case UP:
				sorties = new Sorties_Left();
				pointerExit = sorties.getPointer(direction);
				break;
			case RIGHT:
				sorties = new Sorties_Up();
				pointerExit = sorties.getPointer(direction);
				break;
			case DOWN:
				sorties = new Sorties_Right();
				pointerExit = sorties.getPointer(direction);
				break;
			case LEFT:
				sorties = new Sorties_Left();
				pointerExit = sorties.getPointer(direction);
				break;
			default:
				break;
			}
		} else {
			switch (direction) {
			case UP:
				sorties = new Sorties_Left();
				pointerExit = sorties.getPointer(direction);
				break;
			case RIGHT:
				sorties = new Sorties_Left();
				pointerExit = sorties.getPointer(direction);
				break;
			case DOWN:
				sorties = new Sorties_Up();
				pointerExit = sorties.getPointer(direction);
				break;
			case LEFT:
				sorties = new Sorties_Right();
				pointerExit = sorties.getPointer(direction);
				break;
			default:
				break;
			}
		}

		// Todo : add behaviour in action
		comportement.action(resATraiter);
	}

	public void setCritère1(Ressource res) {
		this.crit1 = new Paquet(res, 1);
	}

	public void setCritère2(Ressource res) {
		this.crit2 = new Paquet(res, 1);
	}

	public Ressource getCritère1() {
		return crit1.getRessource();
	}

	public Ressource getCritère2() {
		return crit2.getRessource();
	}
}