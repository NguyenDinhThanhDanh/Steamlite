package steam.microclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microclient.entities.Client;
import steam.microclient.exceptions.*;
import steam.microclient.service.ClientServiceImpl;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping(value="client")
public class ClientController {
    @Autowired
    ClientServiceImpl clientService;


    @PostMapping(value="/inscription")
    public ResponseEntity<String> inscription(@RequestBody Client client) {
        try {
            this.clientService.createUser(client);
            return ResponseEntity.ok("User inserted!");
        } catch (PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pseudo deja pris");
        }
    }


    @PostMapping(value="/connexion")
    public ResponseEntity<String> connexion(@RequestBody Map<String,String> user){
        String pseudo= user.get("pseudo");
        String mdp=user.get("mdp");
        try {
            Client client = this.clientService.connexion(pseudo, mdp);
            return ResponseEntity.ok("L'utilisateur " + client.getPseudo() + " est connecté ");
        }catch (UtilisateurPasInscritException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client "+pseudo+ " n'existe pas dans steam");
        }
    }
    @PostMapping(value="/{idC}/deconnexion")
    public ResponseEntity<String> deconnexion(@PathVariable int idC){
        try{
            Client client = this.clientService.getClientById(idC);
            this.clientService.deconnexion(client);
            return ResponseEntity.created(URI.create("/deconnexion")).body("L'utilisateur "+client.getPseudo() + " est déconnecté ");
        } catch (OperationNonAutorisee e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorize Operation");
        }
    }
    @GetMapping(value = "/token")
    public ResponseEntity<String> checkToken(@RequestParam String token){
        try {
            return ResponseEntity.ok(this.clientService.checkToken(token));
        } catch (MauvaisTokenException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
