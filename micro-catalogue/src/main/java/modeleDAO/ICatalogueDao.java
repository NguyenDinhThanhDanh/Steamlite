package modeleDAO;

import Entities.Catalogue;

import java.util.List;

public interface ICatalogueDao {
    Catalogue get(long id);

    List<Catalogue> getAll();

    boolean save(Catalogue c);

    boolean update(Catalogue c, String[] params);

    boolean delete(Catalogue c);
}
