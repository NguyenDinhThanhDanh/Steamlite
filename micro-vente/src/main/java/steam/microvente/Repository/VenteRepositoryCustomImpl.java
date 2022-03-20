package steam.microvente.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Bibliotheque;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.GameAlreadyOwnedException;

import java.util.*;
import java.util.stream.Collectors;

@Repository("venteRepositoryCustom")
public class VenteRepositoryCustomImpl implements VenteRepositoryCustom{

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private BibliothequeRepository achatRepository;

    @Override
    public void save(Achat achat) throws GameAlreadyOwnedException {
        //VENTE
        try {
            Vente vente = venteRepository.findById(achat.getIdJeu()).stream().collect(Collectors.toList()).get(0);
            List<Achat> achats = vente.getListeAchats();

            boolean check = achats.stream().anyMatch(histo -> achat.getIdClient() == histo.getIdClient());

            if (!check) {
                achats.add(vente.getListeAchats().size(), achat);
            }
            else{
                throw new GameAlreadyOwnedException();
            }
            vente.setListeAchats(achats);
            venteRepository.delete(venteRepository.findById(achat.getIdJeu()).stream().collect(Collectors.toList()).get(0));
            venteRepository.insert(vente);
        }
        catch (IndexOutOfBoundsException e){

            List<Achat> achats = new ArrayList<>();
            achats.add(achat);

            Vente vente = new Vente(
                    achat.getIdJeu(),
                    achats
            );
            venteRepository.insert(vente);
        }
        //ACHAT
        try {
            Bibliotheque bibliotheque = achatRepository.findById(achat.getIdClient()).stream().collect(Collectors.toList()).get(0);
            List<Achat> achats = bibliotheque.getListeAchats();

            boolean check = achats.stream().anyMatch(histo -> achat.getIdJeu() == histo.getIdJeu());

            if (!check) {
                achats.add(bibliotheque.getListeAchats().size(), achat);
            }
            else{
                throw new GameAlreadyOwnedException();
            }
            bibliotheque.setListeAchats(achats);
            achatRepository.delete(achatRepository.findById(achat.getIdClient()).stream().collect(Collectors.toList()).get(0));
            achatRepository.insert(bibliotheque);
        }
        catch (IndexOutOfBoundsException e){
            List<Achat> achats = new ArrayList<>();
            achats.add(achat);

            Bibliotheque bibliotheque = new Bibliotheque(
                    achat.getIdClient(),
                    achats
            );
            achatRepository.insert(bibliotheque);
        }
    }
}
