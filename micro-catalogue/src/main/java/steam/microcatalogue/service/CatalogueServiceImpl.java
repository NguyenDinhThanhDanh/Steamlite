package steam.microcatalogue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microcatalogue.Entities.Catalogue;
import steam.microcatalogue.Exception.CatalogueInexistantException;
import steam.microcatalogue.Exception.CatalogueNullErrorException;
import steam.microcatalogue.Exception.IdCatalogueUnkownException;
import steam.microcatalogue.Repository.CatalogueRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class CatalogueServiceImpl implements CatalogueService {

    @Autowired
    private CatalogueRepository catalogueRepository;

    public CatalogueServiceImpl() {
    }

    @Override
    public Catalogue findById(Integer id) throws IdCatalogueUnkownException {
        Catalogue c = this.catalogueRepository.findById(id).get();
        if(!c.equals(null)){
            return c;
        }
        else {
            throw new IdCatalogueUnkownException();
        }
    }

    @Override
    public boolean save(Catalogue c) throws CatalogueNullErrorException {
        if(Objects.nonNull(c)){
            catalogueRepository.save(c);
            return true;
        }
        else{
            throw new CatalogueNullErrorException();
        }
    }

    @Override
    public boolean update(Catalogue c, Integer catalogueId) throws CatalogueNullErrorException {
        Catalogue catalogue = catalogueRepository.findById(catalogueId).get();

        if (Objects.nonNull(c.getNomJeu()) && !"".equalsIgnoreCase(c.getNomJeu())) {
            catalogue.setNomJeu(c.getNomJeu());
        }
        else {
            throw new CatalogueNullErrorException();
        }
        if (Objects.nonNull(c.getDateJeu()) && !"".equalsIgnoreCase(c.getDateJeu())) {
            catalogue.setDateJeu(c.getDateJeu());
        }
        else {
            throw new CatalogueNullErrorException();
        }

        if (Objects.nonNull(c.getNomF()) && !"".equalsIgnoreCase(c.getNomF())) {
            catalogue.setNomF(c.getNomF());
        }
        else {
            throw new CatalogueNullErrorException();
        }
        catalogueRepository.save(catalogue);
        return true;
    }

    @Override
    public boolean delete(Integer catalogueId) throws CatalogueInexistantException {
        if (Objects.nonNull(catalogueRepository.findById(catalogueId).get())){
            catalogueRepository.deleteById(catalogueId);
            return true;
        }
        else{
            throw new CatalogueInexistantException();
        }
    }

    @Override
    public Collection<Catalogue> findAll() {
        Collection<Catalogue> catalogues = new ArrayList<>();
        catalogueRepository.findAll().forEach(catalogues::add);
        return catalogues;
    }
}