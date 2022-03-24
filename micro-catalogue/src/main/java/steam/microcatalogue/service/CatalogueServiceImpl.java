package steam.microcatalogue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microcatalogue.Entities.Catalogue;
import steam.microcatalogue.Exception.CatalogueExisteDejaException;
import steam.microcatalogue.Exception.CatalogueInexistantException;
import steam.microcatalogue.Exception.CatalogueNullErrorException;
import steam.microcatalogue.Exception.IdCatalogueUnkownException;
import steam.microcatalogue.Repository.CatalogueRepository;

import javax.xml.catalog.Catalog;
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
        if(!catalogueRepository.existsById(id)){
            throw new IdCatalogueUnkownException();
        }
        Catalogue c = this.catalogueRepository.findById(id).get();
        return c;
    }

    @Override
    public boolean save(Catalogue c) throws CatalogueNullErrorException, CatalogueExisteDejaException {
        Collection<Catalogue> listeCatalogue = new ArrayList<>();
        catalogueRepository.findAll().forEach(listeCatalogue::add);
        for(Catalogue cata : listeCatalogue){
            if(cata.getNomJeu().equals(c.getNomJeu())){
                throw new CatalogueExisteDejaException();
            }
        }
        if(Objects.nonNull(c)){
            catalogueRepository.save(c);
            return true;
        }
        else{
            throw new CatalogueNullErrorException();
        }
    }

    @Override
    public void update(Catalogue c, Integer catalogueId) throws CatalogueNullErrorException, CatalogueInexistantException {
        if(!catalogueRepository.existsById(catalogueId)){
            throw new CatalogueInexistantException();
        }
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
    }

    @Override
    public void delete(Integer catalogueId) throws CatalogueInexistantException {
        if(!catalogueRepository.existsById(catalogueId)){
            throw new CatalogueInexistantException();
        }
        catalogueRepository.delete(catalogueRepository.findById(catalogueId).get());
    }

    @Override
    public Collection<Catalogue> findAll() {
        Collection<Catalogue> catalogues = new ArrayList<>();
        catalogueRepository.findAll().forEach(catalogues::add);
        return catalogues;
    }
}