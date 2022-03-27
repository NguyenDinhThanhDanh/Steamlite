# Projet SteamLite
## _Projet d'intéropérabilité_
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Le projet ***SteamLite*** est le projet à réaliser en groupe pour le second semestre en M2 MIAGE. Le groupe est composé de :
- Edwin CHARLOTTE
- Axel JEAN-FRANCOIS
- Thibaut MURGIA
- Dinh NGUYEN

## SteamLite ?
SteamLite est le nom donné à notre projet. Similaire à Steam mais dans une version plus légère et simple, nous avons décidé qu'il serait possible de s'inscrire en tant que fournisseur ou en tant qu'utilisateur.
Un fournisseur peut ajouter des jeux à une bibliothèque immense de jeux, le catalogue, pour qu'un utilisateur ou client, récupère ces jeux et les ajoute dans sa bibliothèque. Un utilisateur peut communiquer avec d'autres utilisateurs grâce à un service de messagerie.

## Services
Les services suivants composent notre application :
- micro-client
- micro-catalogue
- micro-fournisseur
- micro-social
- micro-vente
- gateway
- service-authen

## Docker
Le projet utilise Docker avec un fichier *docker-compose* et des Dockerfile pour déployer et lancer plus facilement les bases de données requises pour le projet ainsi que les services de chacune des bases.

### Lancer Docker

Pour lancer Docker il faut d'abord avoir Docker sur sa machine.
Il faut ensuite récupérer les images et build les services et volumes : \
`docker-compose up -d`

### Vérifier l'intégralité des containers

Le fichier `docker-compose up -d` doit permettre de télécharger automatiquement les images des bases de données comme un docker pull.

#### Vérifier MySQL

Pour vérifier MySQL, après avoir lancer le `docker-compose up -d` :

1. Ouvrez un nouveau terminal
2. Entrez les commandes suivantes : \
   `docker exec -it mysql-client bash`\
   `>mysql -u root -p projet`\
   `>root`\
   `>>show databases;`\
   `>>use projet;`\
   `>>show tables;`

3. Des tables devraient êtres visibles.

#### Vérifiez MongoDB

Pour vérifier MongoDB, après avoir lancer le *docker-compose* :

1. Ouvrez un nouveau terminal
2. Entrez les commandes suivantes : \
   `docker exec -it mongo_projet mongo`\
   `>mongo`\
   `>>show dbs`\
   `>>use mongo_projet`\
   `>>show collections`
3. Des tables devraient êtres visibles.

### Modifier le *docker-compose*
Il est possible de modifier le fichier pour créer de nouveaux volumes, modifier l'init des bases de données etc. Après l'avoir modifier, il faut refaire les contenairs avec un `docker-compose down` puis un `docker-compose up -d` suffit à relancer les bases de données si tout à bien était fait.

#### Volume MySQL

> volumes: \
- ./bdd/x/setup.sql:/docker-entrypoint-initdb.d/setup.sql\
- ./bdd/x/table.sql:/docker-entrypoint-initdb.d/table.sql\
- ./data/mysql/x:/var/lib/mysql
x étant le microservice que l'on execute.
Attention  les docker-entrypoint devraient normalement s'appeler *1.sql* et *2.sql* car le volume exécute ces lignes dans l'ordre croissant puis alphabétique.

#### Volume MongoDB

> volumes: \
- ./bdd/x/setup.js:/docker-entrypoint-initdb.d/setup.js:ro\
- ./data/mongo/x:/data/db
x étant le microservice que l'on execute.
Pour plus d'aide et de détails suivre ce [lien](https://stackoverflow.com/questions/42912755/how-to-create-a-db-for-mongodb-container-on-start-up)

### Problèmes rencontrés

#### Port déjà utilisé par MySQL

Il est probable que le port donnée dans le fichier *docker-compose* soit déjà utilisé, rendant ainsi impossible l'execution de ce dernier.\
En général le port 3306 est utilisé par MySQL sur votre machine et le container essaie tout de même de faire un port dessus.\
Pour remédier à ce problème : \
`sudo service mysql stop`

#### Pas de base de données projet dans MySQL / Fichier table.sql non lu

Il est possible que le container mysql_projet ait été déjà lancé avant que des modifications sur le docker-compose ou la lecture des fichiers .sql ne soient pris en compte.\
Il faut donc, après avoir lancer `docker-compose up`, `docker-compose up` pour arrêté les containers ou exit\
Supprimer les volumes de docker : `docker volume rm $(docker volume ls -q)`\
Supprimer le dossier data : `sudo rm -rf data/`

> sudo est important car les fichiers de mongo sont sécurisés

Puis de relancer `docker-compose up -d`

#### Container se lance et relance en permanance

Cela se produit lorsque le dossier data/ fait des caprices. Il suffit de `docker-compose down` et de le supprimer.

> sudo est important car les fichiers de mongo sont sécurisés

## URIs

Notre projet FAME doit pour la seconde partie pouvoir gérer les requêtes qu'on lui envoie.
Pour se faire, voici les URIs, les méthodes employées ainsi qu'une brève description.
