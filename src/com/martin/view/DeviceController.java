package com.martin.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class DeviceController {

	@FXML
	Label coordonnées;
	@FXML
	ScrollPane scrollPane;

	public DeviceController() {
	}

	public void initialize() {
		

		//CORRIGER l'ouverture de la fenêtre d'un appareil
		/*
		 * //Set de l'image du destroy et upgrade parce que en jar ca marchait pas via
		 * fxml //Report d'une ouverture de fenêtre
		 * 
		 * //Si c'est un sol remove la tab actions //Sinon remove la tab appareils et
		 * set en disbale le bouton upgrade en fonction de la recherche du //niveau actu
		 * de l'appareil
		 * 
		 * 
		 * //Boucles for pour la grille des descriptions à revoir en fonction de la
		 * taille de TypeAppareil for(int i = 0; i<5; i++) { for(int j = 0; j<2; j++) {
		 * try { //Variables locales name, desc et img de des appareils //A revoir parce
		 * que c'est pas DU TOUT optimisé
		 * 
		 * //Switch en fonction de la longueur faire un objet Affiche ou du genre qui
		 * display //la desc d'un appareil en fonction d'un type appareil switch(i) {
		 * case 0: if(j == 0) { //Ici c'set les acheteurs définitions name desc img
		 * 
		 * if(estLeMêmeAppareil(url)) { styliser(bpt[j][i], url);
		 * 
		 * //Si c'était le même appareil bah on créeait encore des tonnes de variables
		 * 
		 * //Les sets qui vont avec
		 * 
		 * //La c'est spécial c'est un acheteur //On clique dessus ca changeait la
		 * ressource
		 * 
		 * //On oublie pas de positionner comme il faut } }
		 * 
		 * //La c'est plein d'autres appareils basiques qui n'ont pas de truc
		 * particulier //Convoyeurs, Vendeur , Four, Presse, Fils avec les def de name,
		 * desc, et img case 4: if(j == 0) { //Le trieur : les defs de base
		 * 
		 * //clique critère 2 avec un CraftingContrôle du bled (les trucs statics qui
		 * //n'ont rein à faire là)
		 * 
		 * //idem crit 1
		 * 
		 * //Placement de la tab spécial trieur } else { //defs de base //[seulement si
		 * l'appareil correspond] def clique sortie pour choisir le schéma } break; }
		 * 
		 * //Set quand on clique sur la case //couleur et report + set image
		 * 
		 * //Set du positionnement de name, desc, img
		 * 
		 * //Set quand on entre sur la case d'un appareil avec //UN GROS SWITCH DE MORT
		 * 
		 * //Set quand on sort de la case retour au blanc ou gris foncé
		 * 
		 * //Positionnement
		 * 
		 * }catch(FileNotFoundException e) {} } }
		 */

	}
	public void setMainApp(int i, int j, Dashboard dashboard) {
		coordonnées.setText("Hello les appareils !!");
		scrollPane.setContent(dashboard);
	}
}