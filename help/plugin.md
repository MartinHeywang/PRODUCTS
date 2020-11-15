## Créer un plugin

Avoir des connaissances Java est requis pour créer un plugin. Il est recommandé :

-  de connaître les bases de la programmation en Java,
-  d'avoir tout le nécessaire installé sur son ordinateur,
-  de connaître les bases de la programmation orientée objet.

Les différents tutoriels sur la création d'un plugin considère que ces points sont au moins connus / ont été connus de vous.

Par contre, je vous proposent quelques ressources du site [OpenClassroom](https://openclassroom.com), pour vous aider à acquérir ces connaissances :

-  [Installer votre environnement de développement](https://openclassrooms.com/fr/courses/6106191-installez-votre-environnement-de-developpement-java-avec-eclipse),
-  [Les bases de Java et la programmation orientée objet](https://openclassrooms.com/fr/courses/6173501-debutez-la-programmation-avec-java).

#### Etape 1

Créez un projet avec votre IDE ou éditeur de texte. Un projet n'est rien d'autres qu'un dossier sur votre ordinateur qui contient toutes les informations à Java.

#### Etape 2

Ajouter l'API, et éventuellement des kits d'appareils et de ressources au *classpath* du projet.

Vous les télécharger sur [jar-download](https://jar-download.com), en recherchant les fichiers à télécharger; entrez `products-api`, `products-resource-kit`, `products-view-kit`, `products-device-kit`, en fonction de vos besoins.

Tous ces éléments sont publiés au Central Repository :

**Remplacez la version du projet par la version ici** :

-  API : [![products-api-last-version](https://maven-badges.herokuapp.com/maven-central/io.github.martinheywang/products-api/badge.svg)](https://mvnrepository/artifacts/io.github.martinheywang/products-api)

-  Resource Kit : [![products-resource-kit-last-version](https://maven-badges.herokuapp.com/maven-central/io.github.martinheywang/products-resource-kit/badge.svg)](https://mvnrepository/artifacts/io.github.martinheywang/products-resource-kit)
-  Device Kit : [![products-device-kit-last-version](https://maven-badges.herokuapp.com/maven-central/io.github.martinheywang/products-device-kit/badge.svg)](https://mvnrepository/artifacts/io.github.martinheywang/products-device-kit)
-  View Kit : [![products-device-kit-last-version](https://maven-badges.herokuapp.com/maven-central/io.github.martinheywang/products-view-kit/badge.svg)](https://mvnrepository/artifacts/io.github.martinheywang/products-view-kit)

```xml
<dependencies>
    <!-- L'API -->
	<dependency>
    	<groupId>io.github.martinheywang</groupId>
        <artifactId>products-api</artifactId>
        <version>[à remplacer]</version>
        <scope>provided</scope>
    </dependency>
    <!-- Le kit de resources : les resources par défaut dans le jeu -->
    <dependency>
    	<groupId>io.github.martinheywang</groupId>
        <artifactId>products-resource-kit</artifactId>
        <version>[à remplacer]</version>
        <scope>provided</scope>
    </dependency>
    <!-- Le kit d'appareils : les appareils par défaut dans le jeu -->
    <dependency>
    	<groupId>io.github.martinheywang</groupId>
        <artifactId>products-device-kit</artifactId>
        <version>[à remplacer]</version>
        <scope>provided</scope>
    </dependency>
    <!-- Le kit view : des composants graphiques pour le jeu -->
    <dependency>
    	<groupId>io.github.martinheywang</groupId>
        <artifactId>products-view-kit</artifactId>
        <version>[à remplacer]</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Pour rendre plus léger votre plugin, gardez le `scope` de ces dépendances en temps que `provided`. Cela permet que les plugins et l'application utilise la même API et non chacun sa propre version. En plus, cela allège votre plugin.

Aussi, si vous utiliser un kit, vous pouvez ignorez la dépendance vers l'API, car les kits en dépendent eux-même. Cela ne change rien, mais votre *pom.xml* est plus propre.

#### Etape 3

Vous voulez faire un plugin qui ajoute des ressources ? Cliquez [ici](https://github.com/MartinHeywang/PRODUCTS/blob/master/help/resource_plugin.md) !

Vous voulez faire un plugin qui ajoute un / des appareils ? Cliquez [ici](https://github.com/MartinHeywang/PRODUCTS/blob/master/help/device_plugin.md) !

Il est conseillé de faire des plugins séparés si vous voulez ajouter des ressources **et** des appareils : un pour les ressources, un pour les appareils.

#### Etape 4

Packagez votre plugin en *.jar*.

Utilisez votre IDE préféré pour crée le *.jar*. Le plugin ne demande pas de *MANIFEST.MF* particulier, car il n'a pas de `Main-Class`. Si votre plugin dépend d'autres plugins, vous devez l'exclure du *.jar*, mais vous aurez à préciser qu'il est nécessaire. (Cela évite les doublons.)

Si vous utiliser Maven, la meilleur solution est d'utiliser le `maven-assembly-plugin` comme ci-dessous (accéder à la [documentation](http://maven.apache.org/plugins/maven-assembly-plugin/usage.html)).

```xml
<plugin>
	<artifactId>maven-assembly-plugin</artifactId>
    <version>3.3.0</version>
	<configuration>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
    </configuration>
    <executions>
        <execution>
            <id>make-assembly</id>					
            <phase>package</phase>
            <goals>
            	<goal>single</goal>
            </goals>
        </execution>
	</executions>
</plugin>
```

Utiliser `mvn clean package` alors pour générer le *.jar*.

#### Etape 5

Testez votre plugin.

Pour cela, ouvrez l'emplacement du jeu sur votre ordinateur. Vérifiez qu'un fichier `Products-vX.X.X.jar` ou quelque chose du genre existe.

Créez, s'il n'existe pas, un dossier `plugins` dans lequel vous placerez votre fichier *.jar* fraîchement généré. Lancez le jeu, ou redémarrez-le s'il nécessaire, et vérifier que le contenu ajouté est bien disponible et fonctionnel.