package com.martinheywang.model.types;

import java.math.BigInteger;
import java.util.HashMap;

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
import com.martinheywang.model.devices.Template.TemplateModel;
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

	BUYER("Achète les ressources de base.",
			Buyer.class, "500", "30000", "150000", "400", "20000", "120000"),
	SELLER("Vend les ressources et objets \nlui parvenant.",
			Seller.class, "500", "30000", "150000", "400", "20000", "120000"),
	CONVEYOR("Transporte les ressources sur \nla case du bas.",
			Conveyor.class, "100", "20000", "100000", "100", "20000", "100000"),
	RIGHT_CONVEYOR("Transporte les ressources sur \nla case de droite.",
			RightConveyor.class, "100", "20000", "100000", "100", "20000",
			"100000"),
	LEFT_CONVEYOR("Transporte les ressources sur \nla case de gauche.",
			LeftConveyor.class, "100", "20000", "100000", "100", "20000",
			"100000"),
	FURNACE("Fond toutes les ressources en lingots, \nsauf le diamant.",
			Furnace.class, "2000", "50000", "400000", "1800", "40000",
			"350000"),
	PRESS("Transforme toutes les ressources en plaques, \nsauf le diamant.",
			Press.class, "2000", "50000", "400000", "1800", "40000", "350000"),
	WIRE_DRAWER("Transforme les ressources en fil, \nsauf le diamant.",
			WireDrawer.class, "2000", "50000", "400000", "1800", "40000",
			"350000"),
	CONSTRUCTOR("Assemble les ressources pour les \ntransformer en produits.",
			Constructor.class, "20000", "150000", "1000000", "15000", "120000",
			"800000"),
	SORTER("Trie les ressources selon un schéma précis \nde votre décision.",
			Sorter.class, "15000", "125000", "750000", "12500", "110000",
			"720000"),

	FLOOR("Le sol à nu sans appareil. Il ne fait rien.",
			Floor.class, "0", "0", "0", "0", "0", "0");

	private String nom;
	private String url;
	private String desc;
	private HashMap<String, BigInteger> prices;
	private TemplateModel templateModel;
	private Class<? extends Device> classe;

	BaseTypes(String desc,
			Class<? extends Device> classe, String... prices) {
		this.nom = this.toString();
		this.url = this.nom + ".png";
		this.desc = desc;
		this.classe = classe;

		this.prices = new HashMap<>();

		this.prices.put("level_1_build", new BigInteger(prices[0]));
		this.prices.put("level_2_build", new BigInteger(prices[1]));
		this.prices.put("level_3_build", new BigInteger(prices[2]));

		this.prices.put("level_1_delete", new BigInteger(prices[3]));
		this.prices.put("level_2_delete", new BigInteger(prices[4]));
		this.prices.put("level_3_delete", new BigInteger(prices[5]));
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
				"Prix de construction : " + this.getPrice("level_1_build")
						+ " €\n\n"
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
	public TemplateModel getTemplateModel() {
		return templateModel;
	}

	@Override
	public HashMap<String, BigInteger> getPrices() {
		return prices;
	}

	@Override
	public Class<? extends Device> getAssociatedClass() {
		return classe;
	}
}
