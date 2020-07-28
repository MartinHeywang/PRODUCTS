package com.martinheywang.model.types;

import org.pf4j.Extension;

import com.martinheywang.model.behaviours.AbstractBehaviour;
import com.martinheywang.model.behaviours.Buyer;
import com.martinheywang.model.behaviours.Constructor;
import com.martinheywang.model.behaviours.Conveyor;
import com.martinheywang.model.behaviours.Floor;
import com.martinheywang.model.behaviours.Seller;
import com.martinheywang.model.behaviours.Sorter;
import com.martinheywang.model.behaviours.Transform;
import com.martinheywang.model.templates.Template.PointerTypes;
import com.martinheywang.model.templates.TemplateCreator;
import com.martinheywang.model.templates.TemplateModel;
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
			TemplateCreator.getSingleton()
					.setBottom(PointerTypes.EXIT)
					.getModel()),

	SELLER("Vendeur", "Vend les ressources et objets lui parvenant.",
			Seller.class,
			new PricesModule("500", "30000", "150000", "400", "20000",
					"120000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.getModel()),

	CONVEYOR("Convoyeur", "Transporte les ressources sur la case du bas.",
			Conveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setBottom(PointerTypes.EXIT)
					.getModel()),

	LEFT_CONVEYOR("Convoyeur à gauche",
			"Transporte les ressources sur la case de gauche.",
			Conveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setLeft(PointerTypes.EXIT)
					.getModel()),
	FURNACE("Four", "Fond toutes les ressources en lingots, sauf le diamant.",
			Transform.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setBottom(PointerTypes.EXIT)
					.getModel()),
	PRESS("Presse",
			"Transforme toutes les ressources en plaques, sauf le diamant.",
			Transform.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setBottom(PointerTypes.EXIT)
					.getModel()),

	RIGHT_CONVEYOR("Convoyeur à droite",
			"Transporte les ressources sur la case de droite.",
			Conveyor.class,
			new PricesModule("100", "20000", "100000", "100", "20000",
					"100000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setRight(PointerTypes.EXIT)
					.getModel()),

	WIRE_DRAWER("Presse à fil",
			"Transforme les ressources en fil, sauf le diamant.",
			Transform.class,
			new PricesModule("2000", "50000", "400000", "1800", "40000",
					"350000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setBottom(PointerTypes.EXIT)
					.getModel()),

	CONSTRUCTOR("Constructeur",
			"Assemble les ressources pour les transformer en produits.",
			Constructor.class,
			new PricesModule("20000", "150000", "1000000", "15000",
					"120000",
					"800000"),
			TemplateCreator.getSingleton()
					.setTop(PointerTypes.ENTRY)
					.setRight(PointerTypes.EXIT)
					.setLeft(PointerTypes.ENTRY)
					.getModel()),

	SORTER("Trieur",
			"Trie les ressources selon un schéma précis de votre décision.",
			Sorter.class,
			new PricesModule("15000", "125000", "750000", "12500",
					"110000",
					"720000"),
			TemplateCreator.getSingleton()
					.setAll(PointerTypes.EXIT)
					.setTop(PointerTypes.ENTRY)
					.getModel()),

	FLOOR("Sol", "Le sol à nu sans appareil. Il ne fait rien.",
			Floor.class,
			new PricesModule("0", "0", "0", "0", "0", "0"),
			TemplateCreator.getSingleton()
					.getModel());

	private String nom;
	private String url;
	private String desc;
	private PricesModule prices;
	private TemplateModel templateModel;
	private Class<? extends AbstractBehaviour> behaviourClass;
	private Object[] behaviourArgs;

	BaseTypes(String accesibleName, String desc,
			Class<? extends AbstractBehaviour> behaviourClass,
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
	public Class<? extends AbstractBehaviour> getBehaviourClass() {
		return behaviourClass;
	}

	@Override
	public Object[] getBehaviourArgs() {
		return behaviourArgs;
	}
}
