package steam.microvente.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public class Achat {

    @Id
    private int idClient;
    private int idJeu;
    private double prixAchat;
    private String dateAchat;

    public Achat(int idJeu, int idClient, double prixAchat, String dateAchat) {
        this.idJeu = idJeu;
        this.idClient = idClient;
        this.prixAchat = prixAchat;
        this.dateAchat = dateAchat;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
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
