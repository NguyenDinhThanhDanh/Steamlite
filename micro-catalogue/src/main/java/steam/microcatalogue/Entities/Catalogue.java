package steam.microcatalogue.Entities;

import javax.persistence.*;

@Entity
public class Catalogue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nomJeu;
    private String dateJeu;
    private String nomF;
    private long prixJeu;

    public long getPrixJeu() {
        return prixJeu;
    }

    public void setPrixJeu(long prixJeu) {
        this.prixJeu = prixJeu;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomJeu() {
        return nomJeu;
    }

    public void setNomJeu(String nomJeu) {
        this.nomJeu = nomJeu;
    }

    public String getDateJeu() {
        return dateJeu;
    }

    public void setDateJeu(String dateJeu) {
        this.dateJeu = dateJeu;
    }

    public String getNomF() {
        return nomF;
    }

    public void setNomF(String nomF) {
        this.nomF = nomF;
    }
}
