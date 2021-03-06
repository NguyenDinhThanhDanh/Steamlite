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

## Requêtes

### Micro-catalogue

> GET http://localhost:8080/catalogue/jeu

Affiche la liste de tout les jeu , autrement dit ,  le catalogue des jeux postés.

----

>PUT http://localhost:8080/catalogue/jeu/1
>
>Content-Type: application/json
>
>{
>
>"nomJeu" : "Lost Ark",
>
>"dateJeu" : "18/04/2021",
>
>"nomF": "Tripod Studio"
>
>}

Ajoute un jeu au catalogue. Ici , on ajoute Lost Ark , le 18/04/2021 , du studio Tripod Studio.

----

>PUT http://localhost:8080/catalogue/jeu/1
> 
>Content-Type: application/json
> 
>{
> 
>"nomJeu" : "Lost Ark",
> 
>"dateJeu" : "25/04/2021",
> 
>"nomF": "Tripod Studio"
> 
>}

On modifie le jeu qui a un id de 1 , ici Lost Ark.  
On modifie la date , qui passe du 18 avril au 25 avril.

----

>DELETE http://localhost:8080/catalogue/jeu/2

Supprime un jeu du catalogue , ici celui qui a un id de 2.

----

>GET http://localhost:8080/catalogue/jeu/2

Cible un jeu précis du catalogue , ici celui qui a un id de 2.

----

Les autres requêtes sont similaires , mais avec des id différents.

----
----
----

### micro-social

>POST http://localhost:8082/social/message
> 
>Content-Type: application/json
>
>{
> 
>"receveur": 2,
> 
>"envoyeur" : 4,
> 
>"dateChat" : "20/03/2022",
> 
>"message" : "test chien"
> 
>}

Une personne , le receveur d'id 2 , envoi un message " test chien "
à une autre personne , l'envoyeur d'id 4 , le 20/03/2022.

----

>GET http://localhost:8082/social/message
> 
>Content-Type: application/json

Permet d'obtenir la liste de tout les messages de tout les utilisateurs.

----

>GET http://localhost:8082/social/recevoir
> 
>Content-Type: application/json

Permet la réception d'un message de la part d'un utilisateur.

----

>GET http://localhost:8082/social/message/1
>
>Content-Type: application/json

Permet d'obtenir la liste de tout les messages d'un utilisateur ayant un id de 1.

----
----
----

### micro-vente

>GET http://localhost:8080/vente/

Permet d'obtenir la liste de tout les jeux vendus.

----

>POST http://localhost:8080/vente/
> 
>Content-Type: application/json
>
>{
> 
>"idJeu" : "8",
> 
>"idClient" : 3,
> 
>"prixAchat": 30.0,
> 
>"dateAchat": "19/04/2022"
> 
>}

Ajoute à la liste des jeux vendus , le jeu d'id 8 , acheté par le client d'id 3,
à un prix de 30.0€ le 19/04/2022.

----

>GET http://localhost:8080/vente/jeu/7

Permet d'obtenir la liste des ventes liées au jeu d'id 7.

----

>GET http://localhost:8080/vente/client/3

Permet d'obtenir la liste des ventes liées au client d'id 3.

----
----
----

### service-auth

>POST localhost:8081/authent/inscription?pseudo=zed&mdp=zed
>
>{%
> 
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 200, "Le compte n'a pas été créé");
>
>});
>
>%}

Inscrit un utilisateur sous le nom de zed , avec le mot de passe " zed ".  
Le test vérifie que le compte a bien été créé.  
Par exemple , si le pseudo était déjà pris , l'inscription aurait été reffusée.

----

>POST localhost:8081/authent/inscription?pseudo=dad&mdp=dad
>
>{%
> 
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 200, "Le compte n'a pas été créé");
>
>});
>
>%}


Inscrit un deuxième utilisateur sous le nom de dad , avec le mot de passe " dad ".  
Le test vérifie que le compte a bien été créé.  
Par exemple , si le pseudo était déjà pris , l'inscription aurait été reffusée.

----

>POST localhost:8081/authent/inscription?pseudo=zed&mdp=zed
>
>{%
> 
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 409, "Il y aurait du y avoir un conflit");
>
>});
>
>%}

On réessaie d'incrire le premier utilisateur : zed.  
Le test vérifie qu'il doit y avoir un problème , vu que l'utilisateur existe déjà.

----

>POST localhost:8081/authent/connexion?pseudo=zed&mdp=zed
>
>{%
> 
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 200, "Connection");
>
>});
>
>%}

On se connecte avec le pseudo zed , mot de passe zed.  
Le test vérifie que l'on s'est bien connecté.

----

>POST localhost:8081/authent/connexion?pseudo=zed&mdp=zed
>
>{%
>
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 409, "Il y aurait du y avoir un conflit");
>
>});
>
>%}

On se connecte une deuxième fois avec zed.  
Le test vérifie qu'il doit y avoir un problème.  
En effet , on est déjà connecté avec cet utilisateur.  
On ne peut se connecter deux fois de suite.  

----

>POST localhost:8081/authent/token?pseudo=zed&mdp=zed
>
>{%
>
>client.global.set("auth_token", response.headers.valueOf("auth_token"));
>
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 200, "Le token aurait dû être créé");
>
>});
>
>%}

On fabrique le token de l'utilisateur.  
Le test vérifie que le token a bien été créé.

----

>GET localhost:8081/authent/token?token={{auth_token}}
>
>{%
> 
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 200, "Le token aurait dû être valide");
>
>});
>
>%}

On obtient le token d'un utilisateur précis.  
Le test vérifie que le token doit être valide.

----

>GET localhost:8081/authent/token?token=rezarzerar
>
>{%
> 
>client.test("Request executed successfully", function() {
>
>client.assert(response.status === 404, "Le token aurait dû être valide");
>
>});
>
>%}

Permet d'obtenir le token " rezarzerar ".  
Le test vérifie que le token " rezarzerar " existe.