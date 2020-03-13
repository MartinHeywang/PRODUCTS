package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Partie;
import com.martin.model.Coordonnées;
import com.martin.model.Paquet;
import com.martin.model.Ressource;
import com.martin.model.Stock;
import com.martin.model.appareils.comportement.Comportement_Convoyeur;
import com.martin.model.appareils.orientation.Entrées_Center;
import com.martin.model.appareils.orientation.Sorties_Aucune;
import com.martin.model.appareils.orientation.Sorties_Center;
import com.martin.model.appareils.orientation.Sorties_Left;
import com.martin.model.appareils.orientation.Sorties_Right;
import com.martin.model.appareils.orientation.Sorties_Up;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.JeuContrôle;

public class Appareil_Trieur extends Appareil {

	private Paquet crit1, crit2;

	public Appareil_Trieur(Coordonnées xy, NiveauAppareil niveau,
			Direction direction, JeuContrôle controller, Partie partie)
			throws FileNotFoundException {
		super(xy, TypeAppareil.TRIEUR, direction, niveau, controller);

		try {
			if (Connect_SQLite.getPaquetDao().queryBuilder().where()
					.eq("idAppareil", idAppareil).query()
					.size() == 2) {
				crit1 = Connect_SQLite.getPaquetDao().queryBuilder()
						.where()
						.eq("idAppareil", idAppareil)
						.queryForFirst();
				crit2 = Connect_SQLite.getPaquetDao().queryBuilder()
						.where()
						.eq("idAppareil", idAppareil)
						.query().get(1);
			} else {
				crit1 = new Paquet(Ressource.NONE, 1, this);
				crit2 = new Paquet(Ressource.NONE, 1, this);
				Connect_SQLite.getPaquetDao().create(crit1);
				Connect_SQLite.getPaquetDao().create(crit2);
			}
		} catch (SQLException e) {
		}

		entrées = new Entrées_Center();
		pointersEnters = entrées.getPointers(direction);
		sorties = new Sorties_Aucune();
		pointerExit = sorties.getPointer(direction);
		comportement = new Comportement_Convoyeur(xy, niveau,
				pointerExit.getxPlus(),
				pointerExit.getyPlus(), controller);
	}

	@Override
	public void action(Stock resATraiter) throws NegativeArgentException {

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

		comportement = new Comportement_Convoyeur(xy, niveau,
				pointerExit.getxPlus(),
				pointerExit.getyPlus(), controller);
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