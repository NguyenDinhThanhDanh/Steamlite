package steam.microvente.Service;

import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Bibliotheque;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.GameAlreadyOwnedException;
import steam.microvente.Exception.IdClientUnknownException;
import steam.microvente.Exception.IdGameUnkownException;

import java.util.Collection;

public interface ServiceVente {

    void buyGame(Achat achat) throws IdGameUnkownException, IdClientUnknownException, GameAlreadyOwnedException;
    Collection<Vente> getVentesByGameId(int id) throws IdGameUnkownException;
    Collection<Bibliotheque> getVentesByClientId(int id) throws IdClientUnknownException;
    Collection<Vente> getAllVentes();

}
