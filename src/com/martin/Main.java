package com.martin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.martin.view.Accueil2Contrôle;
import com.martin.view.AccueilContrôle;
import com.martin.view.JeuContrôle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * Martin
 * --/--/----
 * 
 * Rôle : Lance l'application*/
public class Main extends Application {
	
	
	public static Stage stage;  //La fenêtre
	
	//La méthode main
	public static void main(String[] args) {
		/*
		 * La ligne ci-dessous éxécute quelques paramètres systèmes puis 
		 * lance la méthode start juste en-dessous.
		 * Elle se charge de faire le setup du programme pour tout poser sur de 
		 * bonnes bases.
		*/
		
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		/*
		 * Le fichier Stats contient des données du jeu qui doivent être accesible 
		 * partout.
		 * La méthode initialize charge les données de la base de données pour un accès plus rapide
		 * et plus clair plus tard dans le programme.*/
		Stats.initialize();
		
		try {
			stage = primaryStage;
			stage.setTitle("PRODUCTS.");
			stage.setResizable(false);
			stage.getIcons().add(new Image(new FileInputStream(new File("images/Icone.png"))));
			//On charge le pseudo
			ResultSet res = Connect_SQLite.getInstance().createStatement().executeQuery(
					"SELECT pseudo FROM infos;");
			//Si le pseudo est vide, on demande le pseudo, sinon on passe à l'étape suivante.
			while(res.next()) {
				if(res.getString(1) != null) {
					initAccueil2();
				}
				else {
					initAccueil();
				}
			}
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}
		stage.show(); //On ouvre la fenêtre/le stage.
	}
	
	
	public void initAccueil() {
		FXMLLoader loader = new FXMLLoader();	//Permet de charger des fichier .fxml
		loader.setLocation(Main.class.getResource("view/Accueil.fxml"));	//Définit l'emplacement où chercher
		
		try {
			BorderPane conteneurPrincipal = (BorderPane) loader.load();		//Charge le fichier dans notre variable de contenu, comme prévu
			Scene scene = new Scene(conteneurPrincipal);	//Fenêtre dans laquelle s'affiche notre contenu
			stage.setScene(scene);	//Montre à la fenêtre quel stage utiliser, et donc son contenu
			
			
			
			AccueilContrôle controler = loader.getController();	//Le contrôleur du fichier en question
			controler.setMainApp(this);	//Petite indication au contrôleur
			
			
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {	//évenements claviers...

				@Override
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER) {	//Si on tape sur entrée
						controler.seConnecter();
					}
				}
				
			});
			
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void initAccueil2() {
		FXMLLoader loader = new FXMLLoader();	//Permet de charger des fichier .fxml
		loader.setLocation(Main.class.getResource("view/Accueil2.fxml"));	//Définit l'emplacement où chercher
		
		try {
			
			
			BorderPane conteneurPrincipal = (BorderPane) loader.load();		//Charge le fichier dans notre variable de contenu, comme prévu
			Scene scene = new Scene(conteneurPrincipal);	//Fenêtre dans laquelle s'affiche notre contenu
			stage.setScene(scene);	//Montre à la fenêtre quel stage utiliser, et donc son contenu
			
			Accueil2Contrôle controler = loader.getController();
			controler.setMainApp(this);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void initGame() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Jeu.fxml"));
			
			BorderPane Bp = (BorderPane) loader.load();
			Scene scene = new Scene(Bp);
			stage.setScene(scene);
			
			stage.setResizable(true);
			
			JeuContrôle controler = loader.getController();
			controler.setMainApp(this);
			
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					try {
							Stats.sauvegarder();
							System.exit(0);
					}catch(SQLException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Petit problème... Nous n'avons pas pu sauvegarder vos données !");
						alert.setContentText("Vous avez peut-être supprimer le fichier de données ! \n"
								+ "Je vous conseille de réinstaller le jeu, même si vos données seront perdu à jamais !!");
						alert.initOwner(stage);
						alert.show();
					}
				}
			});
		} catch (IOException e) {
			System.out.println("ERREUR dans Main dans la méthode " + "initGame. Raison :\n" + e.getLocalizedMessage());
		}
	}
	public Stage getStage() {
		return stage;
	}

}