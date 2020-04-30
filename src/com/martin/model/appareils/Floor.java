package com.martin.model.appareils;

import java.io.FileNotFoundException;

import com.martin.Database;
import com.martin.Main;
import com.martin.model.Coordinates;
import com.martin.model.Packing;
import com.martin.model.appareils.Template.PointerTypes;
import com.martin.model.appareils.Template.TemplateModel;
import com.martin.view.JeuContrôle;
import com.martin.view.SolContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public class Floor extends Device {

	private static TemplateModel templateModel = new TemplateModel(
			PointerTypes.NONE, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE);

	public Floor(DeviceModel model, JeuContrôle controller)
			throws FileNotFoundException {
		super(model, controller);

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(
							Main.class.getResource("view/Floor.fxml"));

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
							new Coordinates(model.getCoordinates().getX(),
									model.getCoordinates().getY()),
							dialog);

					dialog.showAndWait();

					if (dialog.getResult() instanceof Type) {
						DeviceModel newModel = new DeviceModel(
								model.getCoordinates(),
								model.getGame(),
								(Type) dialog
										.getResult(),
								Level.NIVEAU_1,
								Direction.UP);
						controller.setAppareil(
								((Type) dialog.getResult()).getClasse()
										.getConstructor(DeviceModel.class,
												JeuContrôle.class)
										.newInstance(
												newModel,
												controller),
								false);
						Database.daoDeviceModel().delete(model);
						Database.daoDeviceModel().create(newModel);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
	}

	@Override
	public void action(Packing resATraiter) {
		// Here just override the method in case this device is asked for
		// action
		// It souldn't be invoked but it does'nt make the thread overflowed.
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

}
