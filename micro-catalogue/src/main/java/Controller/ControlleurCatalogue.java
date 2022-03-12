package Controller;

import Entities.Catalogue;
import modeleDAO.CatalogueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/catalogue",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControlleurCatalogue {

    @Autowired
    private CatalogueDAO catalogueDAO;

    @GetMapping(value = "/jeu")
    public ResponseEntity<Catalogue> listeJeu(@RequestHeader String token){
        return null;
    }


    @PostMapping(value = "/jeu")
    public ResponseEntity<Catalogue> addJeu(@RequestHeader String token){
        return null;
    }


    @GetMapping(value = "/jeu")
    public ResponseEntity<Catalogue> deleteJeu(@RequestHeader String token){
        return null;
    }
}
