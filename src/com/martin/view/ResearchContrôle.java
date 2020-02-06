package com.martin.view;

import com.martin.Stats;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ResearchContrôle {

	@FXML private Label actuElectricité, prixElectricité,
			actuMaxAcheteur, prixMaxAcheteur,
			  prixSchéma,
			    actuNiveau, prixNiveau;
	@FXML private AnchorPane électricité,
				maxAcheteur,
				  schéma,
				    niveau;
	
	private static boolean électricitéDisable = false;
	private static boolean maxAcheteurDisable = false;
	private static boolean schémaDisable = false;
	private static boolean niveauDisable = false;
	
	public ResearchContrôle() {}
	
	public void initialize() {
		électricité.setDisable(électricitéDisable);
		maxAcheteur.setDisable(maxAcheteurDisable);
		schéma.setDisable(schémaDisable);
		niveau.setDisable(niveauDisable);
		
		actuElectricité.setText("Actuel : "+Stats.électricité);
		actuMaxAcheteur.setText("Actuel : "+Stats.maxAcheteur);
		actuNiveau.setText("Actuel : niveau "+Stats.niveau);
		
		prixElectricité.setText("Coût : "+Stats.prixRechercheElectricité + " €");
		prixMaxAcheteur.setText("Coût : "+Stats.prixRechercheMaxAcheteur + " €");
		prixSchéma.setText("Coût : "+Stats.prixRechercheSchéma + " €");
		prixNiveau.setText("Coût : "+Stats.prixRechercheNiveau + " €");
	}
	
	@FXML private void électricité() {
		System.out.println("électricité");
		if(Stats.électricité > 1 && JeuContrôle.argent.get() >= Stats.prixRechercheElectricité) {
			Stats.électricité--;
			actuElectricité.setText("Actuel : "+Stats.électricité+" €");
			JeuContrôle.argent.set(JeuContrôle.argent.get()-Stats.prixRechercheElectricité);
			
			Stats.prixRechercheElectricité *= 2;
			prixElectricité.setText("Prix : "+Stats.prixRechercheElectricité+" €");
		}
		
		if(Stats.électricité == 1)
			électricitéDisable = true;
		
		électricité.setDisable(électricitéDisable);
	}
	@FXML private void maxAcheteur() {
		System.out.println("maxAcheteur");
		if(Stats.maxAcheteur < 20 && JeuContrôle.argent.get() >= Stats.prixRechercheMaxAcheteur) {
			Stats.maxAcheteur++;
			actuMaxAcheteur.setText("Actuel : "+Stats.maxAcheteur);
			JeuContrôle.argent.set(JeuContrôle.argent.get()-Stats.prixRechercheMaxAcheteur);
			
			Stats.prixRechercheMaxAcheteur *= 2;
			prixMaxAcheteur.setText("Prix : "+Stats.prixRechercheMaxAcheteur+" €");
			
			Stats.prixRechercheSchéma *= 2;
			prixSchéma.setText("Prix : "+Stats.prixRechercheSchéma+" €");
		}
		
		if(Stats.maxAcheteur == 20)
			maxAcheteurDisable = true;
		
		maxAcheteur.setDisable(maxAcheteurDisable);
	}
	@FXML private void schéma() {
		System.out.println("schéma");
		if(Stats.schéma < 20 && JeuContrôle.argent.get() >= Stats.prixRechercheSchéma) {
			Stats.schéma++;
			JeuContrôle.argent.set(JeuContrôle.argent.get()-Stats.prixRechercheSchéma);
			
			
			prixSchéma.setText("Coût : "+Stats.prixRechercheSchéma+" €");
		}
		
		if(Stats.schéma == 20)
			schémaDisable = true;
		
		schéma.setDisable(schémaDisable);
	}
	@FXML private void niveau() {
		System.out.println("niveau");
		if(Stats.niveau < 3 && JeuContrôle.argent.get() >= Stats.prixRechercheNiveau) {
			Stats.niveau++;
			actuNiveau.setText("Actuel : niveau "+Stats.niveau);
			JeuContrôle.argent.set(JeuContrôle.argent.get()-Stats.prixRechercheNiveau);
			
			Stats.prixRechercheNiveau *= 3;
			prixNiveau.setText("Prix : "+Stats.prixRechercheNiveau+" €");
		}
		
		if(Stats.niveau == 3)
			niveauDisable = true;
		
		niveau.setDisable(niveauDisable);
	}
}
