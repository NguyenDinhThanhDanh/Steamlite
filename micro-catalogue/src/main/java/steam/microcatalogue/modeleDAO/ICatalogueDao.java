package steam.microcatalogue.modeleDAO;

import org.springframework.stereotype.Service;
import steam.microcatalogue.Entities.Catalogue;

import java.util.List;

public interface ICatalogueDao {
    Catalogue get(long id);

    List<Catalogue> getAll();

    boolean save(Catalogue c);

    boolean update(Catalogue c, String[] params);

    boolean delete(Catalogue c);
}
