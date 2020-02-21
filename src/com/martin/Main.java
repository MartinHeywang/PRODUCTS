package com.martin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.martin.view.Accueil2Contrôle;
import com.martin.view.AccueilContrôle;
import com.martin.view.JeuContrôle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	
	public static Stage stage;  //La fenêtre
	
	public static void main(String[] args) {
		launch(args);
	
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Définition principale du stage
			stage = primaryStage;
			stage.setTitle("PRODUCTS.");
			stage.setResizable(false);
			stage.getIcons().add(new Image(new FileInputStream(new File("images/Icone.png"))));
			
			//Tests pour savoir si des parties sont disponibles
			if(Connect_SQLite.getPartieDao().queryForAll().size() == 0) {
				//Si il n'y en a pas, alors on dirige l'utilisateur de création de partie
				initAccueil();
			}
			else {
				//Sinon, on le dirige vers la sélection de partie
				initAccueil2();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Petit problème... L'icône n'a pas pu être chargé correctement.");
		} catch (SQLException e) {
			System.err.println("Petit problème... La connexion à la base de données a échoué.");
			e.printStackTrace();
			
		}
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				System.exit(0);
			}
		});
		stage.show(); //Et on oublit pas d'afficher la fenêtre
	}
	
	/**
	 * <h1>initAccueil</h1>
	 * <p>Initialize the stage with the view Accueil.fxml, who corresponds to a the first start page 
	 * (when no login is registered).</p>
	 * 
	 */
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
	/**
	 * <h1>initAccueil2</h1>
	 * <p>Initialize the stage with the view Accueil2.fxml, who demands to load the registered game or to begin a 
	 * new game. (when a login is regestered)</p>
	 */
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
	/**
	 * <h1>initGame</h1>
	 * <p>Initialize the stage with the view Jeu.fxml, who loads all the images and resources to do this game
	 * functionnal.</p>
	 * @throws Exception 
	 */
	public void initGame(Partie partie) throws Exception {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Jeu.fxml"));
			
			BorderPane Bp = (BorderPane) loader.load();
			Scene scene = new Scene(Bp);
			stage.setScene(scene);
			
			stage.setResizable(true);
			
			JeuContrôle controler = loader.getController();
			controler.setMainApp(this, partie);
		} catch (IOException e) {
			System.err.println("ERREUR dans Main dans la méthode " + "initGame. Raison :\n" + e.getLocalizedMessage());
		}
	}

}