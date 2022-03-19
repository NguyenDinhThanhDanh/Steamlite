package steam.microvente.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.IdClientUnknownException;
import steam.microvente.Exception.IdGameUnkownException;
import steam.microvente.Repository.VenteRepository;

import java.util.Collection;

@Service
public class ServiceVenteImpl implements ServiceVente{

    @Autowired
    private VenteRepository venteRepository;

    public ServiceVenteImpl() {

    }

    @Override
    public void buyGame(Vente vente) throws IdGameUnkownException, IdClientUnknownException {
        if(!venteRepository.existsById(vente.getIdJeu())){
            throw new IdGameUnkownException();
        }
        if(!venteRepository.existsById(vente.getIdClient())){
            throw new IdClientUnknownException();
        }
        venteRepository.save(vente);
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
        return null;
    }
}
