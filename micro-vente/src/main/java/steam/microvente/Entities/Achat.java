package steam.microvente.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public class Achat {

    private int idJeu;
    private int idClient;
    private String dateAchat;

    public Achat(int idJeu, int idClient, String dateAchat) {
        this.idJeu = idJeu;
        this.idClient = idClient;
        this.dateAchat = dateAchat;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

}
