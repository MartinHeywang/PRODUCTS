package com.martinheywang.model.types;

import java.math.BigInteger;

import com.martinheywang.model.devices.Buyer;
import com.martinheywang.model.devices.Constructor;
import com.martinheywang.model.devices.Conveyor;
import com.martinheywang.model.devices.Device;
import com.martinheywang.model.devices.Floor;
import com.martinheywang.model.devices.Furnace;
import com.martinheywang.model.devices.LeftConveyor;
import com.martinheywang.model.devices.Level;
import com.martinheywang.model.devices.Press;
import com.martinheywang.model.devices.RightConveyor;
import com.martinheywang.model.devices.Seller;
import com.martinheywang.model.devices.Sorter;
import com.martinheywang.model.devices.WireDrawer;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public enum BaseTypes implements Displayable<BaseTypes>, Type {

	/*
	 * The list of all type that devices can have in the base of the game.
	 * Each contains a name, the class that take care of the device. Note
	 * : the floor must stay in the last position, because this position
	 * is ignored when displaying the Floor.fxml scene.
	 */

	BUYER("Acheteur", "Acheteur.png", "Achète les ressources de base.", "500",
			Buyer.class),
	SELLER("Vendeur", "Vendeur.png",
			"Vend les ressources et objets \nlui parvenant.", "500",
			Seller.class),
	CONVEYOR("Convoyeur", "Convoyeur.png",
			"Transporte les ressources sur \nla case du bas.", "100",
			Conveyor.class),
	RIGHT_CONVEYOR("Convoyeur droite", "Convoyeur_Droite.png",
			"Transporte les ressources sur \nla case de droite.", "100",
			RightConveyor.class),
	LEFT_CONVEYOR("Convoyeur gauche", "Convoyeur_Gauche.png",
			"Transporte les ressources sur \nla case de gauche.", "100",
			LeftConveyor.class),
	FURNACE("Four", "Four.png",
			"Fond toutes les ressources en lingots, \nsauf le diamant.", "2000",
			Furnace.class),
	PRESS("Presse", "Presse.png",
			"Transforme toutes les ressources en plaques, \nsauf le diamant.",
			"2000", Press.class),
	WIRE_DRAWER("Presse à fil", "Appareil_Fil.png",
			"Transforme les ressources en fil, \nsauf le diamant.", "2000",
			WireDrawer.class),
	CONSTRUCTOR("Assembleur", "Assembleur.png",
			"Assemble les ressources pour les \ntransformer en produits.",
			"10000", Constructor.class),
	SORTER("Trieur", "Trieur.png",
			"Trie les ressources selon un schéma précis \nde votre décision.",
			"17500", Sorter.class),

	FLOOR("Sol", "Sol.png", "Le sol à nu sans appareil. Il ne fait rien.", "0",
			Floor.class);

	String nom;
	String url;
	String desc;
	BigInteger prix;
	Class<? extends Device> classe;

	BaseTypes(String nom, String url, String desc, String prix,
			Class<? extends Device> classe) {
		this.nom = nom;
		this.url = url;
		this.desc = desc;
		this.prix = new BigInteger(prix);
		this.classe = classe;
	}

	@Override
	public Displayer<BaseTypes> getDisplayer() {
		BorderPane root = new BorderPane();

		Label nom = new Label();
		nom.setUnderline(true);
		nom.setAlignment(Pos.TOP_CENTER);
		nom.setText(this.getName());
		nom.setWrapText(true);
		root.setTop(nom);

		ImageView image = new ImageView();
		image.setImage(new Image(getClass()
				.getResourceAsStream(Level.LEVEL_1.getURL() + this.getURL())));
		root.setRight(image);

		Label infos = new Label();
		infos.setAlignment(Pos.TOP_CENTER);
		infos.setText(
				"Prix de construction : " + this.getPrice() + " €\n\n"
						+ this.getDescription());
		infos.setWrapText(true);
		root.setLeft(infos);

		root.setPadding(new Insets(3, 3, 3, 3));
		return new Displayer<BaseTypes>(root, this);
	}

	@Override
	public String getName() {
		return this.nom;
	}

	@Override
	public String getURL() {
		return this.url;
	}

	@Override
	public String getDescription() {
		return this.desc;
	}

	@Override
	public BigInteger getPrice() {
		return prix;
	}

	@Override
	public Class<? extends Device> getAssociatedClass() {
		return classe;
	}
}
