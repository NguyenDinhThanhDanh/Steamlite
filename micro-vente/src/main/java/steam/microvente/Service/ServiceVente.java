package steam.microvente.Service;

import steam.microvente.Entities.Vente;
import steam.microvente.Exception.IdClientUnknownException;
import steam.microvente.Exception.IdGameUnkownException;

import java.util.Collection;

public interface ServiceVente {

    void buyGame(Vente vente) throws IdGameUnkownException, IdClientUnknownException;
    Collection<Vente> getVentesByGameId(Integer id);
    Collection<Vente> getVentesByGameName(String name);
    Collection<Vente> getVentesByClientId(Integer id);
    Collection<Vente> getVentesByClientName(String name);
    Collection<Vente> getAllVentes();

}
