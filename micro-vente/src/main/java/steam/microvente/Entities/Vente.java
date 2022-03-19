package steam.microvente.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "ventes")
public class Vente {

    @Id
    private int idJeu;
    private List<Achat> listeAchats;

    public Vente(int idJeu, List<Achat> listeAchats) {
        this.idJeu = idJeu;
        this.listeAchats = listeAchats;
    }

    public List<Achat> getListeAchats() {
        return listeAchats;
    }

    public void setListeAchats(List<Achat> listeAchats) {
        this.listeAchats = listeAchats;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public void setIdJeu(int idJeu) {
        this.idJeu = idJeu;
    }

}
