package steam.microcatalogue.service;

import steam.microcatalogue.Entities.Catalogue;
import steam.microcatalogue.Exception.CatalogueExisteDejaException;
import steam.microcatalogue.Exception.CatalogueInexistantException;
import steam.microcatalogue.Exception.CatalogueNullErrorException;
import steam.microcatalogue.Exception.IdCatalogueUnkownException;

import java.util.Collection;

public interface CatalogueService {
    Catalogue findById(Integer id) throws IdCatalogueUnkownException;
    void update(Catalogue c, Integer catalogueId) throws CatalogueNullErrorException, CatalogueInexistantException;
    void delete(Integer catalogueId) throws CatalogueInexistantException;
    Collection<Catalogue> findAll();

    boolean save(Catalogue c) throws CatalogueNullErrorException, CatalogueExisteDejaException;
}
