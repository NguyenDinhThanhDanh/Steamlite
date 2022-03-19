package steam.microvente.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import steam.microvente.Entities.Vente;

@Repository("venteRepository")
public interface VenteRepository extends MongoRepository<Vente, Integer> {

}
