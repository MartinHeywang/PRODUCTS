package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinate;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.types.BaseTypes;
import com.martinheywang.toolbox.Tools;
import com.martinheywang.view.FloorController;
import com.martinheywang.view.GameController;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public class Floor extends Device {

	public Floor(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					final FXMLLoader loader = Tools.prepareFXMLLoader("Floor");
					final Dialog<Object> dialog = new Dialog<>();
					final DialogPane dialogPane;

					dialogPane = (DialogPane) loader.load();
					dialog.setTitle("Sélectionnez un appareil à construire");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.getMainStage());
					dialog.initModality(Modality.NONE);

					final FloorController SController = loader.getController();
					SController.setMainApp(
							new Coordinate(model.getCoordinates().getX(),
									model.getCoordinates().getY()),
							dialog);

					dialog.showAndWait();

					if (dialog.getResult() instanceof BaseTypes) {
						DeviceModel newModel = new DeviceModel(
								model.getCoordinates(),
								model.getGame(),
								(BaseTypes) dialog
										.getResult(),
								Level.LEVEL_1,
								Direction.UP);
						Database.createDao(DeviceModel.class).delete(model);
						Database.createDao(DeviceModel.class).create(newModel);
						controller.build(
								((BaseTypes) dialog.getResult())
										.getAssociatedClass()
										.getConstructor(DeviceModel.class,
												GameController.class)
										.newInstance(
												newModel,
												controller),
								false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		template = this.model.getType().getTemplateModel().createTemplate(
				model.getCoordinates(),
				model.getDirection());
	}

	@Override
	public void action(Pack resATraiter) {
		// Here just override the method in case this device is asked for
		// action
		// It souldn't be invoked but it does'nt make the thread overflowed.
	}

}
