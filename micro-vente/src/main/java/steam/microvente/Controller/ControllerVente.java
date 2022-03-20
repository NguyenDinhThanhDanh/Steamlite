package steam.microvente.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microvente.Entities.Achat;
import steam.microvente.Entities.Vente;
import steam.microvente.Exception.*;
import steam.microvente.Service.ServiceVente;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/vente",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControllerVente {

    @Autowired
    private ServiceVente serviceVente;

    private final static String URI_vente= "http://localhost:8080/api/auth/token";

    @GetMapping(value = "/")
    public ResponseEntity<Collection<Vente>> listeVentes(){
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

    @GetMapping(value = "/jeu/{id}")
    public ResponseEntity<Collection<Vente>> listeVentesPourJeu(@PathVariable String id){
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

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<Collection<Vente>> listeVentesPourClient(@PathVariable String id) throws IdClientUnknownException {
        Collection<Vente> listeAllVente = serviceVente.getVentesByClientId(Integer.valueOf(id));
        if (listeAllVente.size() == 0){
            return ResponseEntity.noContent().build();
        }
        else{
            ResponseEntity<Collection<Vente>> responseEntity = ResponseEntity.ok(listeAllVente);
            return responseEntity;
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> addJeu(@RequestBody Achat achat){
        try{
            serviceVente.buyGame(achat);
            return ResponseEntity.created(URI.create("/jeu/" + achat.getIdJeu())).body(achat.getIdClient() + " a achété le jeu " + achat.getIdJeu());
        } catch (IdGameUnkownException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id du jeu inconnu");
        } catch (IdClientUnknownException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id de client inconnu");
        } catch (GameAlreadyOwnedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(achat.getIdClient() + " a déjà achété ce jeu");
        }
    }
}
