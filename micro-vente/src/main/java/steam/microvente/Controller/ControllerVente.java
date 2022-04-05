package steam.microvente.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Bibliotheque;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.*;
import steam.microvente.Service.ServiceVente;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

@RestController
@RequestMapping(value = "/vente",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControllerVente {

    @Autowired
    private ServiceVente serviceVente;

    private final static String URI_vente= "http://localhost:8080/api/auth/token";

    @GetMapping(value = "/")
    public ResponseEntity<Collection<Vente>> listeVentes(@RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Collection<Vente> listeAllVente = serviceVente.getAllVentes();
                if (listeAllVente.size() == 0){
                    return ResponseEntity.noContent().build();
                }
                else{
                    ResponseEntity<Collection<Vente>> responseEntity = ResponseEntity.ok(listeAllVente);
                    return responseEntity;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = "/jeu/{id}")
    public ResponseEntity<Collection<Vente>> listeVentesPourJeu(@PathVariable String id, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Collection<Vente> listeAllVente = serviceVente.getVentesByGameId(Integer.valueOf(id));
                if (listeAllVente.size() == 0){
                    return ResponseEntity.noContent().build();
                }
                else{
                    ResponseEntity<Collection<Vente>> responseEntity = ResponseEntity.ok(listeAllVente);
                    return responseEntity;
                }
            } catch (IdGameUnkownException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<Collection<Bibliotheque>> listeVentesPourClient(@PathVariable String id, @RequestHeader(name="token") String token) throws IdClientUnknownException {
        if (checkToken(token)){
            try{
                Collection<Bibliotheque> listeAllVente = serviceVente.getVentesByClientId(Integer.valueOf(id));
                if (listeAllVente.size() == 0){
                    return ResponseEntity.noContent().build();
                }
                else{
                    ResponseEntity<Collection<Bibliotheque>> responseEntity = ResponseEntity.ok(listeAllVente);
                    return responseEntity;
                }
            }
            catch (IdClientUnknownException e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> addJeu(@RequestBody Achat achat, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                serviceVente.buyGame(achat);
                return ResponseEntity.created(URI.create("/jeu/" + achat.getIdJeu())).body(achat.getIdClient() + " a achété le jeu " + achat.getIdJeu());
            } catch (IdGameUnkownException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id du jeu inconnu");
            } catch (IdClientUnknownException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de client inconnu");
            } catch (GameAlreadyOwnedException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(achat.getIdClient() + " a déjà achété ce jeu");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping(value = "/client/{id}")
    public ResponseEntity<String> deleteClientAchats(@PathVariable String id, @RequestHeader(name="token") String token) throws IdClientUnknownException{
        return ResponseEntity.ok().body("ok");
    }

    @DeleteMapping(value = "/jeu/{id}")
    public ResponseEntity<String> deleteJeuVentes(@PathVariable String id, @RequestHeader(name="token") String token) throws IdClientUnknownException{
        if(checkToken(token)){
            try{
                serviceVente.deleteVentesJeu(Integer.valueOf(id));
                return ResponseEntity.ok().body("Les ventes du jeu " + id + " ont été supprimées");
            } catch (IdGameUnkownException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de jeu inconnu");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public boolean checkToken(String token){
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/client/token?token="+token)).GET().build();
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
