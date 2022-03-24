package steam.microcatalogue.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import steam.microcatalogue.Entities.Catalogue;

@Repository
public interface CatalogueRepository  extends MongoRepository<Catalogue, Integer> {
}
