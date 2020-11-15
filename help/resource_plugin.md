##### Si vous êtes ici, vous voulez créer un plugin ajoutant des ressources.

Créez une énumération implémentant `io.github.martinheywang.products.api.model.resource.Resource`.

Voici un exemple : 

```java
// devrait contenir products.plugin.resource
package com.yourname.products.plugin.resource;

import io.github.martinheywang.products.api.model.resource.Resource;
import java.math.BigInteger;

import org.pf4j.Extension;

@Extension // A ne pas oublier
public enum Main implements Resource {
    
    SOMETHING("Something", "140", "Something.png");
    
    private String name;
    private BigInteger value;
    private String url;
    
    Main(String name, String price, String file) {
		this.name = name;
		this.value = new BigInteger(price);
		this.url = Main.class.getResource("/images/resources/"+file).toExternalForm();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BigInteger getPrice() {
		return this.price;
	}

	@Override
	public String getURL() {
		return this.url;
	}
}
```

Trois variables sont importantes :

-  *name* : le nom en jeu
-  *value* : la valeur à la vente par défaut
-  *url* : le chemin **absolu** vers l'image de la ressource.

**Note : vous pouvez créer autant d'énumération que vous voulez tant qu'elle respecte ce modèle.**

Vous trouverez des exemples d'énumération de ressources dans ce `repository` :

-  [Les minerais]([L'acheteur](https://github.com/MartinHeywang/PRODUCTS/blob/master/products-resource-kit/src/main/java/io/github/martinheywang/products/kit/resource/Ore.java)),
-  [Les produits]([L'acheteur](https://github.com/MartinHeywang/PRODUCTS/blob/master/products-resource-kit/src/main/java/io/github/martinheywang/products/kit/resource/Product.java)).

**Si l'idée vous viendra, vous ne DEVEZ PAS implémenter la méthode `toString()` de `Object`. Le programme l'utilise à des fins bien précis. Cela pourrait causer des problèmes.**

