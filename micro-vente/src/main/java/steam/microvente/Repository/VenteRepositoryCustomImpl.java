package steam.microvente.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;
import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Vente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("venteRepositoryCustom")
public class VenteRepositoryCustomImpl implements VenteRepositoryCustom{

    @Autowired
    private VenteRepository venteRepository;

    @Override
    public void save(Achat achat) {
        try {
            Vente vente = venteRepository.findById(achat.getIdJeu()).stream().collect(Collectors.toList()).get(0);
            List<Achat> achats = vente.getListeAchats();
            achats.add(vente.getListeAchats().size(), achat);
            vente.setListeAchats(achats);

            venteRepository.delete(venteRepository.findById(achat.getIdJeu()).stream().collect(Collectors.toList()).get(0));
            venteRepository.insert(vente);
        }
        catch (Exception e){

            List<Achat> achats = new ArrayList<>();
            achats.add(achat);

            Vente vente = new Vente(
                    achat.getIdJeu(),
                    achats
            );
            venteRepository.insert(vente);
        }


//        Document ticket = new Document();
//
//        Document ventesDuJeu = new Document("_id", vente.getIdJeu());
//        Document test = new Document().value();
    }
}
