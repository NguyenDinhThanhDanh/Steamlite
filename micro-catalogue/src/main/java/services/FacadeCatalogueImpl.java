package services;

import Entities.Catalogue;
import modeleDAO.CatalogueDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class FacadeCatalogueImpl implements FacadeCatalogue{

    private CatalogueDAO catalogueDAO;
}
