package steam.microvente.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.IdClientUnknownException;
import steam.microvente.Exception.IdGameUnkownException;
import steam.microvente.Repository.VenteRepository;
import steam.microvente.Repository.VenteRepositoryCustom;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceVenteImpl implements ServiceVente{

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private VenteRepositoryCustom venteRepositoryCustom;

    public ServiceVenteImpl() {

    }

    @Override
    public void buyGame(Achat achat) throws IdGameUnkownException, IdClientUnknownException {
//        if(!venteRepository.existsById(vente.getIdJeu())){
//            throw new IdGameUnkownException();
//        }
//        if(!venteRepository.existsById(vente.getIdClient())){
//            throw new IdClientUnknownException();
//        }
        venteRepositoryCustom.save(achat);
        //venteRepository.save(achat);
    }

    @Override
    public Collection<Vente> getVentesByGameId(Integer id) {
        return null;
    }

    @Override
    public Collection<Vente> getVentesByGameName(String name) {
        return null;
    }

    @Override
    public Collection<Vente> getVentesByClientId(Integer id) {
        return null;
    }

    @Override
    public Collection<Vente> getVentesByClientName(String name) {
        return null;
    }

    @Override
    public Collection<Vente> getAllVentes() {
        return StreamSupport
                .stream(venteRepository
                        .findAll()
                        .spliterator(), false)
                .collect(Collectors.toList());
    }
}
