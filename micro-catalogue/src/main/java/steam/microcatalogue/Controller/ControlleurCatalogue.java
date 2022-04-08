package steam.microcatalogue.Controller;

import steam.microcatalogue.Entities.Catalogue;
import steam.microcatalogue.Exception.CatalogueExisteDejaException;
import steam.microcatalogue.Exception.CatalogueInexistantException;
import steam.microcatalogue.Exception.CatalogueNullErrorException;
import steam.microcatalogue.Exception.IdCatalogueUnkownException;
import steam.microcatalogue.service.CatalogueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

@RestController
@RequestMapping(value = "/catalogue",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControlleurCatalogue {

    @Autowired
    private CatalogueServiceImpl catalogueDAO;

    private final static String URI_CLIENT= "http://localhost:8080/client/token?token=";

    @GetMapping(value = "/jeu")
    public ResponseEntity<Collection<Catalogue>> listeJeu(@RequestHeader(name="token") String token){
        if (checkToken(token)){
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
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping(value = "/jeu")
    public ResponseEntity<String> addJeu(@RequestBody Catalogue catalogue, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                catalogueDAO.save(catalogue);
                return ResponseEntity.created(URI.create("/catalogue/jeu/" + catalogue.getId())).body(catalogue.getNomJeu() + " a été ajouté au catalogue de jeu");
            } catch (CatalogueExisteDejaException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("le catalogue passé existe déjà");
            } catch (CatalogueNullErrorException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le catalogue passé est incorrect");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @DeleteMapping(value = "/jeu/{id}")
    public ResponseEntity<String> deleteJeu(@PathVariable String id, @RequestHeader(name="token") String token) {
        if (checkToken(token)){
            try {
                catalogueDAO.delete(Integer.valueOf(id));
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("le jeu d'id" + id + " a été supprimé du catalogue de jeu");
            } catch (CatalogueInexistantException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("le catalogue passé est incorrect ou n'existe pas");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/jeu/{id}")
    public ResponseEntity<Catalogue> getJeu(@PathVariable String id, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Catalogue c = catalogueDAO.findById(Integer.valueOf(id));
                ResponseEntity<Catalogue> responseEntity = ResponseEntity.ok(c);
                return responseEntity;
            } catch (IdCatalogueUnkownException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping(value = "/jeu/{id}")
    public ResponseEntity<String> updateJeu(@RequestBody Catalogue catalogue, @PathVariable String id, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                catalogueDAO.update(catalogue, Integer.valueOf(id));
                return ResponseEntity.created(URI.create("/catalogue/jeu/" + catalogue.getId())).body(catalogue.getNomJeu() + " a été ajouté au catalogue de jeu");
            } catch (CatalogueNullErrorException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le catalogue n'existe pas");
            } catch (CatalogueInexistantException e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public boolean checkToken(String token){
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_CLIENT+token)).GET().build();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                return true;
            }
            else {
                return false;
            }
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

