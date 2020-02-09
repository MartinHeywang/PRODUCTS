package com.martin.model.appareils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.martin.Connect_SQLite;
import com.martin.Main;
import com.martin.model.Coordonnées;
import com.martin.model.LocatedImage;
import com.martin.model.Ressource;
import com.martin.model.appareils.comportement.Comportement;
import com.martin.model.appareils.comportement.Comportement_Aucun;
import com.martin.model.appareils.orientation.Entrées;
import com.martin.model.appareils.orientation.Entrées_Aucune;
import com.martin.model.appareils.orientation.Sorties;
import com.martin.model.appareils.orientation.Sorties_Aucune;
import com.martin.model.exceptions.NegativeArgentException;
import com.martin.view.AppareilsContrôle;
import com.martin.view.JeuContrôle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

public abstract class Appareil extends ImageView{
	
	// FAIRE les méthodes initializeData()
	// FAIRE les méthodes getPrix()
	// FAIRE les méthodes destructions()
	// FAIRE les listes publiques statiques de coordonnées de référencement des appareils
	
	protected ArrayList<Ressource> ressources = new ArrayList<Ressource>();
	
	protected TypeAppareil type;
	protected Direction direction;
	protected NiveauAppareil niveau;
	protected Coordonnées xy;
	
	protected Comportement comportement = new Comportement_Aucun();
	protected Sorties sorties = new Sorties_Aucune();
	protected Entrées entrées = new Entrées_Aucune();
	
	protected JeuContrôle controller;
	
	protected static int électricité = 5;
	protected Direction pointerExit;
	protected Direction pointerEnter;
	
	
	/**
	 * @author Martin
	 * 26 janv. 2020 | 11:30:04
	 * 
	 * <b>Appareil.init</b>
	 * <p>Crée un objet Appareil.</p>
	 * 
	 * Args :
	 * @param xy les coordonnées de l'appareil construit
	 * @param type le type d'appareil
	 * @param direction l'orientation de l'appareil
	 * @param niveau le niveau de l'appareil
	 * @param controller le controller de l'interface de jeu
	 * 
	*/
	protected Appareil(Coordonnées xy, TypeAppareil type, Direction direction, NiveauAppareil niveau,
			JeuContrôle controller) throws FileNotFoundException {
		super(new LocatedImage(niveau.getURL()+type.getURL()));
		
		//Définition de l'objet avec les paramètres demandés
		this.xy = xy;
		this.type = type;
		this.direction = direction;
		this.niveau = niveau;
		this.controller = controller;
		
		//Quand on clique sur l'image
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					//Charger et ouvrir la boîte de dialogue demandant l'action à effectuer
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(Main.class.getResource("view/Appareil.fxml"));
					
					Dialog<TypeAppareil> dialog;
					DialogPane dialogPane;
					
					dialogPane = (DialogPane) loader.load();
					dialog = new Dialog<TypeAppareil>();
					dialog.setTitle("Sélection d'appareil - PRODUCTS.");
					dialog.setDialogPane(dialogPane);
					dialog.initOwner(Main.stage);
					dialog.initModality(Modality.NONE);
					
					AppareilsContrôle controller = loader.getController();
					controller.setMainApp(xy.getX(), xy.getY());
					
					dialog.showAndWait();
					
				}catch(IOException e) {
					/*Si une erreur est survenue lors du chargement de la fenêtre,
					 afficher le message plus la raison donnée par Java.*/
					System.out.println("ERREUR dans Appareil.java entre les lignes 59 et 79 excluses."
							+ "Raison :\n"+e.getMessage());
				}
			}
		});
		try {
			Connect_SQLite.getInstance().createStatement()
			.executeUpdate("UPDATE appareils SET '"+(xy.getX()+1)+"' = "
					+ "'"+type.toString()+"*"+niveau.toString()+"|"+direction.toString()+"' WHERE id = "+(xy.getY()+1));
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			
		}
	}
	/**
	 * 
	 * @param resATraiter la ressource à traiter par l'appareil lors de son action
	 */
	public void action(Ressource resATraiter) throws NegativeArgentException{
		if(this.controller.getGrilleAppareils(new Coordonnées(xy.getX()+pointerExit.getxPlus(), 
				xy.getY()+pointerExit.getyPlus())).getXy().isNearFrom(xy)){
			
			if(pointerExit.equals(controller.getGrilleAppareils(new Coordonnées(xy.getX()+pointerExit.getxPlus()
					, xy.getY()+pointerExit.getyPlus())).getPointerEnter()) || this 
					instanceof Appareil_Vendeur)
				comportement.action(resATraiter);
		}
	}
	
	/**
	 * @author Martin
	 * 26 janv. 2020 | 11:25:02
	 * 
	 * <b>destruction</b>
	 * <p>Cette méthode permet d'effacer toutes le données stockées par les appareils
	 * (base de données, coordonnées...)</p>
	 * 
	*/
	public void destruction(){
		//FAIRE méthode destruction
	}
	/**
	 * @return the ressources
	 */
	public ArrayList<Ressource> getRessources() {
		return ressources;
	}
	/**
	 * @return the type
	 */
	public TypeAppareil getType() {
		return type;
	}
	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}
	/**
	 * 
	 * @return the pointerEnter
	 */
	public Direction getPointerEnter() {
		return pointerEnter;
	}
	/**
	 * 
	 * @return the pointerExit
	 */
	public Direction getPointerExit() {
		return pointerExit;
	}
	/**
	 * @return the niveau
	 */
	public NiveauAppareil getNiveau() {
		return niveau;
	}
	/**
	 * @return the xy
	 */
	public Coordonnées getXy() {
		return xy;
	}
	/**
	 * @return the comportement
	 */
	public Comportement getComportement() {
		return comportement;
	}
	/**
	 * @return the sorties
	 */
	public Sorties getSorties() {
		return sorties;
	}
	/**
	 * @return the entrées
	 */
	public Entrées getEntrées() {
		return entrées;
	}
	/**
	 * @return the controller
	 */
	public JeuContrôle getController() {
		return controller;
	}
	/**
	 * @return the électricité
	 */
	public static int getÉlectricité() {
		return électricité;
	}
	/**
	 * @param ressources the ressources to set
	 */
	public void setRessources(ArrayList<Ressource> ressources) {
		this.ressources = ressources;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TypeAppareil type) {
		this.type = type;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	/**
	 * @param niveau the niveau to set
	 */
	public void setNiveau(NiveauAppareil niveau) {
		this.niveau = niveau;
	}
	/**
	 * @param xy the xy to set
	 */
	public void setXy(Coordonnées xy) {
		this.xy = xy;
	}
	/**
	 * @param comportement the comportement to set
	 */
	public void setComportement(Comportement comportement) {
		this.comportement = comportement;
	}
	/**
	 * @param sorties the sorties to set
	 */
	public void setSorties(Sorties sorties) {
		this.sorties = sorties;
	}
	/**
	 * @param entrées the entrées to set
	 */
	public void setEntrées(Entrées entrées) {
		this.entrées = entrées;
	}
	/**
	 * @param controller the controller to set
	 */
	public void setController(JeuContrôle controller) {
		this.controller = controller;
	}
	/**
	 * @param électricité the électricité to set
	 */
	public static void setÉlectricité(int électricité) {
		Appareil.électricité = électricité;
	}
	
	
	
}
