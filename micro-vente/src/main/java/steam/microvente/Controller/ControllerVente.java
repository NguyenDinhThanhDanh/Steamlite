package steam.microvente.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/vente")
    public ResponseEntity<Collection<Vente>> listeVent(){
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

    @PostMapping(value = "/vente")
    public ResponseEntity<String> addJeu(@RequestBody Vente vente){
        try{
            serviceVente.buyGame(vente);
            return ResponseEntity.created(URI.create("/vente/jeu/" + vente.getIdJeu())).body(vente.getIdClient() + " a achété le jeu");
        } catch (IdGameUnkownException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Id du jeu inconnu");
        } catch (IdClientUnknownException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id de client inconnu");
        }
    }

}
