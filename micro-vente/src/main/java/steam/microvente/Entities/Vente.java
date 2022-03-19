package steam.microvente.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "ventes")
public class Vente {

    @Id
    private int idJeu;
    private int idClient;
    private int idVente;
    private String dateAchat;

    public Vente(int idJeu, int idClient, String dateAchat) {
        this.idJeu = idJeu;
        this.idClient = idClient;
        this.dateAchat = dateAchat;
    }

    public int getIdVente() {
        return idVente;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public void setIdVente(int idVente) {
        this.idVente = idVente;
    }
}
