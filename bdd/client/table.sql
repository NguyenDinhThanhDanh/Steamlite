﻿USE projet;

DROP TABLE IF EXISTS ABONNEMENT;
DROP TABLE IF EXISTS UTILISATEUR;

CREATE TABLE UTILISATEUR(
    idU INT AUTO_INCREMENT NOT NULL,
    nomU VARCHAR(25),
    prenomU VARCHAR(25),
    dateNaissU DATE,
    mdp VARCHAR(255),
    pseudo VARCHAR(255),
    CONSTRAINT PK_UTILISATEUR PRIMARY KEY(idU),
    UNIQUE(pseudo)
);

CREATE TABLE ABONNEMENT(
    idA INT AUTO_INCREMENT NOT NULL,
    nomA VARCHAR(25),
    prixA INT,
    dateDebutAbonnement DATE,
    dateFinAbonnement DATE,
    renouvellement tinyint(1),
    actif tinyint(1),
    idU INT NOT NULL,
    CONSTRAINT PK_ABONNEMENT PRIMARY KEY(idA),
    CONSTRAINT FK_UTILISATEUR_ABONNEMENT FOREIGN KEY(idU)
    REFERENCES UTILISATEUR(idU)
);