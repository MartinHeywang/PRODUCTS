package com.martinheywang.view;

import com.martinheywang.model.Coordinates;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class DeviceController {

	@FXML
	Label coordinates;
	@FXML
	ScrollPane scrollPane;

	public DeviceController() {
	}

	public void initialize() {

		// CORRIGER l'ouverture de la fen�tre d'un appareil
		/*
		 * //Set de l'image du destroy et upgrade parce que en jar ca marchait
		 * pas via fxml //Report d'une ouverture de fen�tre //Si c'est un sol
		 * remove la tab actions //Sinon remove la tab appareils et set en
		 * disbale le bouton upgrade en fonction de la recherche du //niveau
		 * actu de l'appareil //Boucles for pour la grille des descriptions �
		 * revoir en fonction de la taille de TypeAppareil for(int i = 0; i<5;
		 * i++) { for(int j = 0; j<2; j++) { try { //Variables locales name,
		 * desc et img de des appareils //A revoir parce que c'est pas DU TOUT
		 * optimis� //Switch en fonction de la longueur faire un objet Affiche
		 * ou du genre qui display //la desc d'un appareil en fonction d'un
		 * type appareil switch(i) { case 0: if(j == 0) { //Ici c'set les
		 * acheteurs d�finitions name desc img if(estLeM�meAppareil(url)) {
		 * styliser(bpt[j][i], url); //Si c'�tait le m�me appareil bah on
		 * cr�eait encore des tonnes de variables //Les sets qui vont avec
		 * //La c'est sp�cial c'est un acheteur //On clique dessus ca
		 * changeait la ressource //On oublie pas de positionner comme il faut
		 * } } //La c'est plein d'autres appareils basiques qui n'ont pas de
		 * truc particulier //Convoyeurs, Vendeur , Four, Presse, Fils avec
		 * les def de name, desc, et img case 4: if(j == 0) { //Le trieur :
		 * les defs de base //clique crit�re 2 avec un CraftingContr�le du
		 * bled (les trucs statics qui //n'ont rein � faire l�) //idem crit 1
		 * //Placement de la tab sp�cial trieur } else { //defs de base
		 * //[seulement si l'appareil correspond] def clique sortie pour
		 * choisir le sch�ma } break; } //Set quand on clique sur la case
		 * //couleur et report + set image //Set du positionnement de name,
		 * desc, img //Set quand on entre sur la case d'un appareil avec //UN
		 * GROS SWITCH DE MORT //Set quand on sort de la case retour au blanc
		 * ou gris fonc� //Positionnement }catch(FileNotFoundException e) {} }
		 * }
		 */

	}

	public void setMainApp(Coordinates xy, Dashboard dashboard) {
		coordinates.setText("X: " + xy.getX() + " Y: " + xy.getY());
		scrollPane.setContent(dashboard);
		dashboard.prefWidthProperty().bind(scrollPane.widthProperty());
		dashboard.prefHeightProperty().bind(scrollPane.heightProperty());
	}
}