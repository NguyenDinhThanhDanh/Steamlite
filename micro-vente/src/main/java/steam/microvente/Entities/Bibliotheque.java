package steam.microvente.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "achats")
public class Bibliotheque {

    @Id
    private int idClient;
    private List<Achat> listeAchats;

    public Bibliotheque(int idClient, List<Achat> listeAchats) {
        this.idClient = idClient;
        this.listeAchats = listeAchats;
    }

    public List<Achat> getListeAchats() {
        return listeAchats;
    }

    public void setListeAchats(List<Achat> listeAchats) {
        this.listeAchats = listeAchats;
    }

    public int getIdJeu() {
        return idClient;
    }

    public void setIdJeu(int idClient) {
        this.idClient = idClient;
    }

}
