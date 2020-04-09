package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.Main;
import com.martin.model.Coordonnees;
import com.martin.model.Stock;
import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.view.JeuContrôle;
import com.martin.view.SolContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public class Appareil_Sol extends Appareil {

	private EventHandler<MouseEvent> onClicked = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("view/Sol.fxml"));

				Dialog<Object> dialog;
				DialogPane dialogPane;

				dialogPane = (DialogPane) loader.load();
				dialog = new Dialog<Object>();
				dialog.setTitle("Sélectionnez un appareil à construire");
				dialog.setDialogPane(dialogPane);
				dialog.initOwner(Main.stage);
				dialog.initModality(Modality.NONE);

				SolContrôle SController = loader.getController();
				SController.setMainApp(
						new Coordonnees(model.getCoordonnees().getX(),
								model.getCoordonnees().getY()),
						dialog);

				dialog.showAndWait();

				if (dialog.getResult() instanceof Type) {
					controller.setAppareil(
							((Type) dialog.getResult()).getClasse()
									.getConstructor(Coordonnees.class,
											Direction.class,
											Niveau.class,
											JeuContrôle.class)
									.newInstance(model.getCoordonnees(),
											Direction.UP,
											Niveau.NIVEAU_1,
											controller),
							false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public Appareil_Sol(AppareilModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		this.setOnMouseClicked(onClicked);

		entrances = Entrées.listForNone();
		exits = Sorties.listForNone();

		// Todo : add behaviour
	}

	@Override
	public void action(Stock resATraiter) {
	}

}
