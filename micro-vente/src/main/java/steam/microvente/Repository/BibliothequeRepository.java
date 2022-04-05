package steam.microvente.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import steam.microvente.Entities.Bibliotheque;

public interface BibliothequeRepository extends MongoRepository<Bibliotheque, Integer> {
}
