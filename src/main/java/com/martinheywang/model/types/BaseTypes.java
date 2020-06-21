package com.martinheywang.model.types;

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
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.devices.WireDrawer;
import com.martinheywang.model.types.info.PricesModule;
import com.martinheywang.view.Displayable;
import com.martinheywang.view.Displayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * This enum represents all the static data about the different base
 * devices.
 * 
 * @author Heywang
 *
 */
public enum BaseTypes implements Displayable<BaseTypes>, Type {

	BUYER("Acheteur", "Achète les ressources de base.",
			Buyer.class,
			new PricesModule("500", "30000", "150000", "400", "20000",
					"120000"),
			new TemplateModel(PointerTypes.NONE, PointerTypes.NONE,
					PointerTypes.EXIT, PointerTypes.NONE)),
	SELLER("Vendeur", "Vend les ressources et objets lui parvenant.",
			Seller.class,
			new PricesModule("500", "30000", "150000", "400", "20000",
					"120000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
					PointerTypes.NONE)),
	CONVEYOR("Convoyeur", "Transporte les ressources sur la case du bas.",
			Conveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE,
					PointerTypes.EXIT,
					PointerTypes.NONE)),
	RIGHT_CONVEYOR("Convoyeur à droite",
			"Transporte les ressources sur la case de droite.",
			RightConveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.NONE,
					PointerTypes.NONE)),
	LEFT_CONVEYOR("Convoyeur à gauche",
			"Transporte les ressources sur la case de gauche.",
			LeftConveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
					PointerTypes.EXIT)),
	FURNACE("Four", "Fond toutes les ressources en lingots, sauf le diamant.",
			Furnace.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,
					PointerTypes.NONE)),
	PRESS("Presse",
			"Transforme toutes les ressources en plaques, sauf le diamant.",
			Press.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,
					PointerTypes.NONE)),
	WIRE_DRAWER("Presse à fil",
			"Transforme les ressources en fil, sauf le diamant.",
			WireDrawer.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,
					PointerTypes.NONE)),
	CONSTRUCTOR("Constructeur",
			"Assemble les ressources pour les transformer en produits.",
			Constructor.class,
			new PricesModule("20000", "150000", "1000000", "15000",
					"120000",
					"800000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.EXIT,
					PointerTypes.NONE,
					PointerTypes.ENTRY)),
	SORTER("Trieur",
			"Trie les ressources selon un schéma précis de votre décision.",
			Sorter.class,
			new PricesModule("15000", "125000", "750000", "12500",
					"110000",
					"720000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.EXIT,
					PointerTypes.EXIT)),

	FLOOR("Sol", "Le sol à nu sans appareil. Il ne fait rien.",
			Floor.class,
			new PricesModule("0", "0", "0", "0", "0", "0"),
			new TemplateModel(
					PointerTypes.NONE, PointerTypes.NONE, PointerTypes.NONE,
					PointerTypes.NONE));

	private String nom;
	private String url;
	private String desc;
	private PricesModule prices;
	private TemplateModel templateModel;
	private Class<? extends Device> classe;

	BaseTypes(String accesibleName, String desc, Class<? extends Device> classe,
			PricesModule prices, TemplateModel templateModel) {
		this.nom = accesibleName;
		this.url = this.toString() + ".png";
		this.desc = desc;
		this.templateModel = templateModel;
		this.classe = classe;
		this.prices = prices;
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
				"Prix de construction : " + this.getPrices().getLevel1Build()
						+ " €\n\n"
						+ this.getDescription());
		infos.setWrapText(true);
		infos.setMaxWidth(200d);
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
	public PricesModule getPrices() {
		return prices;
	}

	@Override
	public Class<? extends Device> getAssociatedClass() {
		return classe;
	}
}
