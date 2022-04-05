package steam.microvente.Repository;

import org.springframework.stereotype.Repository;
import steam.microvente.Entities.Achat;
import steam.microvente.Exception.GameAlreadyOwnedException;


@Repository("venteRepositoryCustom")
public interface VenteRepositoryCustom{
    public void save(Achat achat) throws GameAlreadyOwnedException;
}
