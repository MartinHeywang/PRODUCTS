package com.martin.view;

import java.sql.SQLException;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.Stats;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ProgressContrôle {

	@FXML Label label;
	@FXML ProgressBar progressBar;
	@FXML Button continuer, annuler;
	
	Stage stg;
	Main main;
	
	
	
	
	private static DoubleProperty current_progress = new SimpleDoubleProperty(0.0);
	
	public ProgressContrôle() {}
	
	public void initialize() {
		progressBar.setProgress(0.0);
		current_progress.bind(progressBar.progressProperty());
		
		
	}
	
	@FXML private void continuer() {
		
		label.setText("Opération en cours...");
		progressBar.setProgress(0.0);
			
		Task<Void> tâche = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				int i = 0;
				
				for(int x = 0; x < 20; x++) {
					for(int y = 0; y < 20; y++) {
						
						try {
							Connect_SQLite.getInstance().createStatement().executeUpdate(
									"UPDATE appareils SET '"+(x+1)+"' = \"SOL*NIVEAU_1|UP\" WHERE id = "+(y+1)+";");
							Connect_SQLite.getInstance().createStatement().executeUpdate(
									"UPDATE appareils_infos SET '"+(x+1)+"' = \"NONE\" WHERE id = "+(y+1)+";");
						}catch (SQLException e) {
							e.printStackTrace();
						}
						
						i++;
						this.updateProgress(i, 409);
					}
				}
				
				try {
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET argent = 1250;");
					i++;
					this.updateProgress(i, 409);
					
					
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixAcheteur = 500;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixAssembleur = 7500;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixConvoyeur = 1000;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixFour = 2000;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixPresse = 2000;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixPresseFil = 3000;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixTrieur = 10000;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixVendeur = 500;");
					i++;
					this.updateProgress(i, 409);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET tailleGrille = 3;");
					i++;
					this.updateProgress(i, 410);
					Connect_SQLite.getInstance().createStatement().executeUpdate(
							"UPDATE infos SET prixAgrandissement = 50000;");
					i++;
					this.updateProgress(i, 411);
							
					Platform.runLater(new Runnable() {
						public void run() {
							Stats.initialize();
							if(main.getMP() != null)
								main.getMP().stop();
							main.initGame();
							main.getStage().setMaximized(true);
							main.music();
							stg.close();
						}
					});
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
			
		};
		
		progressBar.progressProperty().bind(tâche.progressProperty());
		
		Thread t = new Thread(tâche);
		t.setDaemon(true);
		t.start();
	}
	@FXML private void annuler() {
		stg.close();
	}
	
	public void setMainApp(Stage stg, Main main) {
		this.stg = stg;	
		this.main = main;
	}
	
	public void addProgress(double value) {
		progressBar.setProgress(progressBar.getProgress()+value);
	}
	
}
