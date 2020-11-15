# products-api

L'API pour obtenir des données relatives au jeu et ce qui se passe en temps réel. A utiliser lors de la création de plugin. Elle contient les classes à implémenter pour créer des ressources et des appareils.

>  Pour voir la javadoc, c'est [ici](https://javadoc.io/doc/io.github.martinheywang/products-api/latest) !

### Utiliser l'API dans votre programme

##### Vous utilisez un *build tool* : 

Si vous utilisez Maven, insérez cette dépendance :

```xml
<dependency>
	<groupId>io.github.martinheywang</groupId>
    <artifactId>products-api</artifactId>
    <version>[replace with latest version]</version>
</dependency>
```

Dernière version : [![latest_version](https://maven-badges.herokuapp.com/maven-central/io.github.martinheywang/products-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.martinheywang/products-api/)

>   Si vous utilisez d'autres *build tool*, cest [ici](https://mvnrepository.com/artifacts/io.github.martinheywang/products-api/latest) !

##### Vous n'utilisez pas de *build tool*  :

Pas de problème. Allez [ici](https://jar-download.com/artifacts/io.github.martinheywang/products-api) pour télécharger la dernière version. Ajouter TOUS les jars au *classpath*. Supprimez les doublons si nécessaire.

Si vous ne savez pas comment modifier le *classpath*, sous Eclipse IDE, il suffit de faire clic-droit sur le projet et de sélectionner *Build Path > Configure Build Path*. Ensuite cliquez sur *Add External Jars* et sléectionnez les fichiers que vous venez de télécharger.

Si vous utilisez un autre logiciel, contribuez à ce projet en ajoutant comment faire avec votre système...