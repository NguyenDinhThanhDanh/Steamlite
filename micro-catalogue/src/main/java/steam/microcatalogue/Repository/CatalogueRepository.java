package steam.microcatalogue.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import steam.microcatalogue.Entities.Catalogue;

@Repository
public interface CatalogueRepository  extends CrudRepository<Catalogue, Integer> {
    @Modifying
    @Query("delete from Catalogue c where c.id = ?1")
    void deleteCatalogueById(Integer id);

    @Query("select count(c)>0 from Catalogue c where c.id = ?1")
    boolean existsById(Integer id);
}
