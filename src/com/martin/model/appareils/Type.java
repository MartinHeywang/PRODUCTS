package com.martin.model.appareils;

public enum Type {

	/*
	 * La liste des appareils actuellement en jeu. Chacun contient plein
	 * d'informations nécessaires, comme le nom, la classe qui le gère...
	 * L'ouverture de la fenêtre de Appareil.fxml se sert de cette enum
	 * pour afficher les nombres de appareils notamment. Si on en enlève
	 * un, il disparaît de la fenêtre... Note : le sol ne devant pas
	 * s'afficher, il doit ABSOLUMENT rester en dernière position.
	 */

	ACHETEUR("Acheteur", "Acheteur.png", "Achète les ressources de base.", 500,
			Appareil_Acheteur.class),
	VENDEUR("Vendeur", "Vendeur.png",
			"Vend les ressources et objets \nlui parvenant.", 500,
			Appareil_Vendeur.class),
	CONVOYEUR("Convoyeur", "Convoyeur.png",
			"Transporte les ressources sur \nla case du bas.", 100,
			Appareil_Convoyeur.class),
	CONVOYEUR_DROITE("Convoyeur droite", "Convoyeur_Droite.png",
			"Transporte les ressources sur \nla case de droite.", 100,
			Appareil_Convoyeur_Droite.class),
	CONVOYEUR_GAUCHE("Convoyeur gauche", "Convoyeur_Gauche.png",
			"Transporte les ressources sur \nla case de gauche.", 100,
			Appareil_Convoyeur_Gauche.class),
	FOUR("Four", "Four.png",
			"Fond toutes les ressources en lingots, \nsauf le diamant.", 2000,
			Appareil_Four.class),
	PRESSE("Presse", "Presse.png",
			"Transforme toutes les ressources en plaques, \nsauf le diamant.",
			2000, Appareil_Presse.class),
	PRESSE_FIL("Presse à fil", "Appareil_Fil.png",
			"Transforme les ressources en fil, \nsauf le diamant.", 2000,
			Appareil_Presse_Fil.class),
	ASSEMBLEUR("Assembleur", "Assembleur.png",
			"Assemble les ressources pour les \ntransformer en produits.",
			10_000, Appareil_Assembleur.class),
	TRIEUR("Trieur", "Trieur.png",
			"Trie les ressources selon un schéma précis \nde votre décision.",
			17500, Appareil_Trieur.class),

	SOL("Sol", "Sol.png", "Le sol à nu sans appareil. Il ne fait rien.", 0,
			Appareil_Sol.class);

	// Le nom, l'url, la description et la classe
	String nom;
	String url;
	String desc;
	int prix;
	Class<? extends Appareil> classe;

	// Un seul et unique constructeur
	Type(String nom, String url, String desc, int prix,
			Class<? extends Appareil> classe) {
		this.nom = nom;
		this.url = url;
		this.desc = desc;
		this.prix = prix;
		this.classe = classe;
	}

	// Les getters
	public String getNom() {
		return this.nom;
	}

	public String getURL() {
		return this.url;
	}

	public String getDescription() {
		return this.desc;
	}

	public int getPrix() {
		return prix;
	}

	public Class<? extends Appareil> getClasse() {
		return classe;
	}
}
