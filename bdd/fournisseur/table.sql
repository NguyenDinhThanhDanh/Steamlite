USE service_fournisseur;

DROP TABLE IF EXISTS FOURNISSEUR;

CREATE TABLE FOURNISSEUR(
                            idF INT AUTO_INCREMENT NOT NULL,
                            nomF VARCHAR(255),
                            mdpF VARCHAR(255),
                            dateInscriptionF DATE,
                            CONSTRAINT PK_FOURNISSEUR PRIMARY KEY(idF),
                            UNIQUE(nomF)
);
