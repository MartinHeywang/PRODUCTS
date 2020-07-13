package com.martinheywang.model.types;

import org.pf4j.Extension;

import com.martinheywang.model.behaviours.Behaviour;
import com.martinheywang.model.behaviours.Buyer;
import com.martinheywang.model.behaviours.Constructor;
import com.martinheywang.model.behaviours.Conveyor;
import com.martinheywang.model.behaviours.Floor;
import com.martinheywang.model.behaviours.Seller;
import com.martinheywang.model.behaviours.Sorter;
import com.martinheywang.model.behaviours.Transform;
import com.martinheywang.model.devices.Template.PointerTypes;
import com.martinheywang.model.devices.Template.TemplateModel;
import com.martinheywang.model.types.info.PricesModule;

/**
 * This enum represents all the static data about the different base
 * devices.
 * 
 * @author Heywang
 *
 */
@Extension
public enum BaseTypes implements Type {

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
	LEFT_CONVEYOR("Convoyeur à gauche",
			"Transporte les ressources sur la case de gauche.",
			Conveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.NONE,
					PointerTypes.EXIT)),
	FURNACE("Four", "Fond toutes les ressources en lingots, sauf le diamant.",
			Transform.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,
					PointerTypes.NONE)),
	PRESS("Presse",
			"Transforme toutes les ressources en plaques, sauf le diamant.",
			Transform.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.NONE, PointerTypes.EXIT,
					PointerTypes.NONE)),
	RIGHT_CONVEYOR("Convoyeur à droite",
			"Transporte les ressources sur la case de droite.",
			Conveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			new TemplateModel(
					PointerTypes.ENTRY, PointerTypes.EXIT, PointerTypes.NONE,
					PointerTypes.NONE)),
	WIRE_DRAWER("Presse à fil",
			"Transforme les ressources en fil, sauf le diamant.",
			Transform.class,
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
	private Class<? extends Behaviour> behaviourClass;
	private Object[] behaviourArgs;

	BaseTypes(String accesibleName, String desc,
			Class<? extends Behaviour> behaviourClass,
			PricesModule prices, TemplateModel templateModel,
			Object... behaviourArgs) {

		this.nom = accesibleName;
		this.url = this.toString() + ".png";
		this.desc = desc;
		this.templateModel = templateModel;
		this.behaviourClass = behaviourClass;
		this.prices = prices;
		this.behaviourArgs = behaviourArgs;

		Type.addReferences(this);
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
	public Class<? extends Behaviour> getBehaviourClass() {
		return behaviourClass;
	}

	@Override
	public Object[] getBehaviourArgs() {
		return behaviourArgs;
	}
}
