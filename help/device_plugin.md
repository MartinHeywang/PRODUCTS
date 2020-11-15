##### Si vous êtes ici, vous voulez créer un plugin ajoutant des appareils.

Créer une classe étendant `io.github.martinheywang.products.api.model.device.Device` ou n'importe qu'elle autre classe fille.

Voici un exemple : 

```java
// devrait contenir products.plugin.device
package com.yourname.products.plugin.device;

import java.util.Arrays;
import java.util.List;
import io.github.martinheywang.products.api.model.Coordinate;
import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.DeviceModel;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.exception.MoneyException;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import javafx.scene.Node;

@Extension // A ne pas oublier
public class Example extends Device {
    
    public Example(DeviceModel model){
        super(model);
    }
    
    @Override
    public Action act(Pack resources) throws MoneyException {
        final Action action = new Action(this, resources);
        
        // Calculer différents options en fonction des arguments
        
        // Ces différents sont à modifier en fonction du résultat de l'action.
        action.setOutput(output); // La coordonnée de l'appareil à activer
		action.setGivenPack(resources); // Le pack à donner à l'appareil suivant
		action.addCost(this.getActionCost()); // Le coût de l'action
		action.markAsSuccessful(); // Défini l'action comme réussi (irréversible)
        
        // Renvoyer l'action
        return action;
    }
    
    @Override
    public List<Node> getWidgets(){
        // Ne jamais retourner null !
        return Arrays.asList();
    }
}
```

Vous avez deux méthodes à implémenter :

-  `act(Pack)` : Réalise l'action de l'appareil à partir du pack réalisé. Retourne une `Action` qui représente ce qui s'est passé. 
-  `getWidgets()` : Retourne une liste de widgets. Les widgets sont des composants qui sont affichés sur le menu de l'appareil. Cette méthode doit retourner, si aucun widget n'est demandé, une liste vide, et pas `null`. Ces widgets sont des `javafx.scene.Node`.

Vous trouverez des exemples d'appareils existants sur ce `repository` :

-  [Le convoyeur](https://github.com/MartinHeywang/PRODUCTS/blob/master/products-device-kit/src/main/java/io/github/martinheywang/products/kit/device/Conveyor.java) (un des plus simples, sans widgets),
-  [Le four](https://github.com/MartinHeywang/PRODUCTS/blob/master/products-device-kit/src/main/java/io/github/martinheywang/products/kit/device/Furnace.java) (un peu plus compliqué, sans widgets),
-  [L'acheteur](https://github.com/MartinHeywang/PRODUCTS/blob/master/products-device-kit/src/main/java/io/github/martinheywang/products/kit/device/Buyer.java) (assez simple, utilisation d'un widget),
-  [Le constructeur](https://github.com/MartinHeywang/PRODUCTS/blob/master/products-device-kit/src/main/java/io/github/martinheywang/products/kit/device/Constructor.java) (complexe, utilisation de plusieurs widgets).