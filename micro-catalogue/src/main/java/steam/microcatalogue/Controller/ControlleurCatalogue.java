package steam.microcatalogue.Controller;

import steam.microcatalogue.Entities.Catalogue;
import steam.microcatalogue.Exception.CatalogueInexistantException;
import steam.microcatalogue.service.CatalogueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/catalogue",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControlleurCatalogue {

    @Autowired
    private CatalogueServiceImpl catalogueDAO;

    private final static String URI_Catalogue= "http://localhost:8080/api/auth/token";

    @GetMapping(value = "/jeu")
    public ResponseEntity<Collection<Catalogue>> listeJeu(@RequestHeader String token){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_PILEOUFACE+"?token="+token)).GET().build();*/
        try{
            Collection<Catalogue> listeAllJeu = catalogueDAO.findAll();
            if (listeAllJeu.size() == 0){
                return ResponseEntity.noContent().build();
            }
            else{
                ResponseEntity<Collection<Catalogue>> responseEntity = ResponseEntity.ok(listeAllJeu);
                return responseEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }


    @PostMapping(value = "/jeu")
    public ResponseEntity<String> addJeu(@RequestBody Catalogue catalogue){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            Object statutAjout = catalogueDAO.save(catalogue);
            if (statutAjout.equals(1)){
                return ResponseEntity.created(URI.create("/catalogue/jeu/" + catalogue.getId())).body(catalogue.getNomJeu() + " a été ajouté au catalogue de jeu");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le catalogue passé est incorrect ou existe déjà");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("problème avec la requête");
    }


    @DeleteMapping(value = "/jeu")
    public ResponseEntity<String> deleteJeu(@RequestHeader String token, @RequestParam Integer Idcatalogue) {
        /*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try {
            boolean statutDelete = catalogueDAO.delete(Idcatalogue);
            if (statutDelete) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("le jeu d'id" + Idcatalogue + " a été supprimé du catalogue de jeu");
            }
        } catch (CatalogueInexistantException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le catalogue passé est incorrect ou n'existe pas");
        }

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("problème avec la requête");
    }
}

