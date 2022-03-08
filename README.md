
# Projet SteamLite

Le projet ***SteamLite*** est le projet à réaliser en groupe pour le second semestre en M2 MIAGE. Le groupe est composé de :

- Edwin CHARLOTTE
- Axel JEAN-FRANCOIS
- Thibaut MURGIA
- Dinh NGUYEN

## Docker

Le projet utilise Docker avec un fichier *docker-compose* pour déployer et lancer plus facilement les bases de données requises pour le projet, à savoir MySQL et MongoDB.

### Lancer Docker

Pour lancer Docker il faut d'abord avoir Docker sur sa machine.
Il est ensuite nécessaire de récupérer les images afin de créer les containers ainsi que les volumes avec les base de données. \
`docker-compose up -d`

### Vérifier l'intégralité des containers

Le fichier *docker-compose* doit permettre de télécharger automatiquement les images des bases de données comme un docker pull.

#### Vérifier MySQL

Pour vérifier MySQL, après avoir lancer le *docker-compose* :

1. Ouvrez un nouveau terminal
2. Entrez les commandes suivantes : \
   `docker exec -it mysql-client bash`\
   `mysql -u root -p projet`\
   `Enter password: root`\
   `show databases;`\
   `use projet;`\
   `show tables;`

3. Les tables *CLIENT* et *ABONNEMENT* sont normalement visibles.

#### Vérifiez MongoDB

Pour vérifier MongoDB, après avoir lancer le *docker-compose* :

1. Ouvrez un nouveau terminal

2. Entrez les commandes suivantes : \
   `docker exec -it mongo_projet mongo`\
   `mongo`\
   `show dbs`\
   `use mongo_projet`\
   `show collections`

3. Des entrées devraient êtres visibles si nous avons ajouter des données tests au préalable dans le *docker-compose*. \
   Il est possible de regarder à quoi ressemble les données dans base_de_donnees/insert.js

### Modifier le *docker-compose*

Il est possible de modifier le fichier pour créer de nouveaux volumes, modifier l'init des bases de données etc. Après l'avoir modifier, un `docker-compose up` suffit à relancer les bases de données si tout à bien était fait.

#### Volume MySQL

> volumes: \
- ./base_de_donnees/setup.sql:/docker-entrypoint-initdb.d/setup.sql\
- ./base_de_donnees/table.sql:/docker-entrypoint-initdb.d/table.sql\
- ./data/mysql:/var/lib/mysql

Attention  les docker-entrypoint devraient normalement s'appeler *1.sql* et *2.sql* car le volume exécute ces lignes dans l'ordre croissant puis alphabétique.

#### Volume MongoDB

> volumes: \
- ./base_de_donnees/setup.js:/docker-entrypoint-initdb.d/setup.js:ro\
- ./data/mongo:/data/db

Pour plus d'aide et de détails suivre ce [lien](https://stackoverflow.com/questions/42912755/how-to-create-a-db-for-mongodb-container-on-start-up)

### Problèmes rencontrés

#### Port déjà utilisé par MySQL

Il est probable que le port donnée dans le fichier *docker-compose* soit déjà utilisé, rendant ainsi impossible l'execution de ce dernier.\
En général le port 3306 est utilisé par MySQL sur votre machine et le container essaie tout de même de faire un port dessus.\
Pour remédier à ce problème : \
`sudo service mysql stop`

#### Pas de base de données projet dans MySQL / Fichier table.sql non lu

Il est possible que le container mysql_projet ait été déjà lancé avant que des modifications sur le docker-compose ou la lecture des fichiers .sql ne soient pris en compte.\
Il faut donc, après avoir lancer `docker-compose up`, ctrl-c pour arrêté les containers ou exit\
Supprimer les containers après les avoir arrêtés : `docker rm $(docker ps -aq)`\
Supprimer les volumes de docker : `docker volume rm $(docker volume ls -q)`\
Supprimer le dossier data : `sudo rm -rf data/`

> sudo est important car les fichiers de mongo sont sécurisés \

Puis de relancer `docker-compose up`

### AES

Nous avons utiliser un code AES de cette source \
https://howtodoinjava.com/java/java-security/java-aes-encryption-example/

## URIs

Notre projet FAME doit pour la seconde partie pouvoir gérer les requêtes qu'on lui envoie.
Pour se faire, voici les URIs, les méthodes employées ainsi qu'une brève description.

| URI               | Méthode | Description                                                                                         |
|-------------------|:-------:|-----------------------------------------------------------------------------------------------------|
| /fame             |   GET   | Aucune idée de ce qu'on a actuellement                                                              |
| /fame/inscription |   POST  | Ajout et inscrit un nouvel utilisateur dans la base de données. Retourne une erreur si déjà inscrit |
| /fame/inscription |   DELETE| Désinscrit un utilisateur. Retourne une erreur si inconnu                                           |
| /fame/utilisateur |GET      |                                                                                                     |
| /fame/utilisateur/id| GET   | Get les informations en fonction d'un id                                                            |
| /fame/transport   | POST    | Créer un nouveau transport                                                                          |
| /fame/transport   | GET     | Récupère la liste des transports                                                                    |
| /fame/utilisateur/id|  POST | Acheter un ticket                                                                                   |
| /fame/utilisateur/id|  GET  | Liste des tickets associés à un utilisateur(id)                                                     |
| /fame/utilisateur/abonner| POST    | L'utilisateur s'abonne a un abonnement                                                       |
| /fame/utilisateur/abonner| DELETE  | L'utilisateur supprime son abonnement                                                        |
| /fame/utilisateur/abonner| PUT     | L'utilisateur change d'abonnement                                                            |
| /fame/abonnement   | POST   | Créer un abonnement pour un utilisateur                                                             |

