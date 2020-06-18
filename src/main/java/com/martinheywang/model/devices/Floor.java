package com.martinheywang.model.devices;

import java.io.FileNotFoundException;

import com.martinheywang.Main;
import com.martinheywang.model.Coordinates;
import com.martinheywang.model.Pack;
import com.martinheywang.model.database.Database;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
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

	private static final TemplateModel templateModel = new TemplateModel(
			PointerTypes.NONE, PointerTypes.NONE, PointerTypes.NONE,
			PointerTypes.NONE);

	public Floor(DeviceModel model, GameController controller)
			throws FileNotFoundException {
		super(model, controller);

		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					final FXMLLoader loader = Tools.prepareFXMLLoader("Floor");

					Dialog<Object> dialog;
					DialogPane dialogPane;

					dialogPane = (DialogPane) loader.load();
					dialog = new Dialog<Object>();
					dialog.setTitle("Sélectionnez un appareil à construire");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.getMainStage());
					dialog.initModality(Modality.NONE);

					final FloorController SController = loader.getController();
					SController.setMainApp(
							new Coordinates(model.getCoordinates().getX(),
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
						controller.setAppareil(
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

		template = templateModel.createTemplate(model.getCoordinates(),
				model.getDirection());
	}

	@Override
	public void action(Pack resATraiter) {
		// Here just override the method in case this device is asked for
		// action
		// It souldn't be invoked but it does'nt make the thread overflowed.
	}

	@Override
	protected TemplateModel getTemplateModel() {
		return templateModel;
	}

}
