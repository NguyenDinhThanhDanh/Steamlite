package steam.microcatalogue.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import steam.microcatalogue.Entities.Catalogue;

@Repository
public interface CatalogueRepository  extends CrudRepository<Catalogue, Integer> {
}
