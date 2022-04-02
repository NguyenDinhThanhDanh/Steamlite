package steam.microvente.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Bibliotheque;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.GameAlreadyOwnedException;
import steam.microvente.Exception.IdClientUnknownException;
import steam.microvente.Exception.IdGameUnkownException;
import steam.microvente.Repository.BibliothequeRepository;
import steam.microvente.Repository.VenteRepository;
import steam.microvente.Repository.VenteRepositoryCustom;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceVenteImpl implements ServiceVente{

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private VenteRepositoryCustom venteRepositoryCustom;

    @Autowired
    private BibliothequeRepository bibliothequeRepository;

    public ServiceVenteImpl() {

    }

    @Override
    public void buyGame(Achat achat) throws IdGameUnkownException, IdClientUnknownException, GameAlreadyOwnedException {
//        if(!venteRepository.existsById(achat.getIdJeu())){
//            throw new IdGameUnkownException();
//        }
//        if(!venteRepository.existsById(achat.getIdClient())){
//            throw new IdClientUnknownException();
//        }
        try {
            venteRepositoryCustom.save(achat);
        }
        catch (GameAlreadyOwnedException e){
            throw new GameAlreadyOwnedException();
        }

    }

    @Override
    public Collection<Vente> getVentesByGameId(int id) throws IdGameUnkownException {
        if(!venteRepository.existsById(id)){
            throw new IdGameUnkownException();
        }
        return venteRepository.findById(id).stream().collect(Collectors.toList());
    }


    @Override
    public Collection<Bibliotheque> getVentesByClientId(int id) throws IdClientUnknownException {
        if(!bibliothequeRepository.existsById(id)){
            throw new IdClientUnknownException();
        }
        return bibliothequeRepository.findById(id).stream().collect(Collectors.toList());
    }

    @Override
    public Collection<Vente> getAllVentes() {
        return StreamSupport
                .stream(venteRepository
                        .findAll()
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteVentesJeu(int id) throws IdGameUnkownException {
        if(!venteRepository.existsById(id)){
            throw new IdGameUnkownException();
        }
        venteRepository.deleteById(id);
    }

    @Override
    public void deleteAchatsClient(int id) throws IdGameUnkownException {

    }
}
