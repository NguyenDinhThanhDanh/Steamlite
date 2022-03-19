package steam.microvente.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import steam.microvente.Entities.Vente;

@Repository("venteRepository")
public interface VenteRepository extends CrudRepository<Vente, Integer> {

}
