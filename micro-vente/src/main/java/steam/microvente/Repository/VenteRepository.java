package steam.microvente.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import steam.microvente.Entities.Vente;

import java.util.Collection;
import java.util.List;

@Repository("venteRepository")
public interface VenteRepository extends MongoRepository<Vente, Integer> {

    @Query(value = "{'listeAchats.idClient': ?0}", fields = "{'_id' : 1, listeAchats : 0}")
    Collection<Vente> findVenteByIdClient(int id);

    @Query(value = "{'_id': ?0}", fields = "{'listeAchats.idClient' : 1}")
    List<Vente> findClientByIdGame(int id);

    @Override
    void deleteById(Integer integer);
}
