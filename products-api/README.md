# products-api

>  Une API est une interface programmable, c'est-à-dire qu'elle permet d'accéder aux données d'une application ou d'un service via un programme.

## Description

L'API de PRODUCTS. est une API qui ne sert uniquement à l'application et la création de plugins. Quand vous créez l'un d'eux, vous êtes obligés de l'utiliser. Les différents *kits*, disponibles d'en d'autres modules, utilisent cette API pour créer des ressources et des appareils.

### Utiliser l'API dans votre projet

>  Dans un premier temps, il vous faudra créer un projet. Un projet est un dossier de votre ordinateur qui contiendra tout ce qui est relatif à votre projet.

Pour utiliser l'API dans votre projet, télécharger la dernière version [ici](https://jar-download.com/artifacts/io.github.martinheywang/products-api), ajoutez la au *classpath*. 

-  [Qu'est-ce que le classpath et comment le modifier ?](https://github.com/MartinHeywang/PRODUCTS/blob/master/help/classpath.md)

L'API est également publié au Central Repository. Voici le tag à insérer pour Maven. Utiliser la dernière version disponible est préférable.

```xml
<dependency>
	<groupId>io.github.martinheywang</groupId>
    <artifactId>products-api</artifactId>
    <version>1.0.1</version>
</dependency>
```

Si vous utilisez d'autres *build tool*, vous retrouverez le tag à insérer [ici](https//mvnrepository.com/artifact/io.github.martinheywang/products-api).

## Vue d'ensemble

Une fois cette API configurée, vous pouvez créer des appareils et resources comme bon vous semble.

