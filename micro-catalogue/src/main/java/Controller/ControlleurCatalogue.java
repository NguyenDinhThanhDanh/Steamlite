package Controller;

import Entities.Catalogue;
import modeleDAO.CatalogueDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/catalogue",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControlleurCatalogue {

    @Autowired
    private CatalogueDAO catalogueDAO;

    private final static String URI_Catalogue= "http://localhost:8080/api/auth/token";

    @GetMapping(value = "/jeu")
    public ResponseEntity<Collection<Catalogue>> listeJeu(@RequestHeader String token){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_PILEOUFACE+"?token="+token)).GET().build();*/
        try{
            Collection<Catalogue> listeAllJeu = catalogueDAO.getAll();
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
            boolean statutAjout = catalogueDAO.save(catalogue);
            if (statutAjout){
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
    public ResponseEntity<String> deleteJeu(@RequestHeader String token, @RequestBody Catalogue catalogue){
        /*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            boolean statutDelete = catalogueDAO.delete(catalogue);
            if (statutDelete){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(catalogue.getNomJeu() + " a été supprimé du catalogue de jeu");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le catalogue passé est incorrect ou n'existe déjà");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("problème avec la requête");
    }
}

