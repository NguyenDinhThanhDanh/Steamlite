package steam.serviceauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microclient.exceptions.*;
import steam.serviceauth.entities.Client;
import steam.serviceauth.exception.ClientDejaConnecte;
import steam.serviceauth.exception.UtilisateurPasInscritException;
import steam.serviceauth.service.ClientServiceImpl;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/authent")
public class ControllerClient {

    @Autowired
    ClientServiceImpl clientService;

    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscription(@RequestParam String pseudo, @RequestParam String mdp) {
        try {
            this.clientService.createUtilisateur(mdp, pseudo, LocalDate.now().toString());
            return ResponseEntity.ok("Le compte a bien été crée");
        }catch (PseudoDejaPrisException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pseudo"+pseudo+"deja pris");
        }
    }
    @PostMapping(value="/connexion")
    public ResponseEntity<String> connexion(@RequestParam String pseudo,@RequestParam String mdp){
        try {
            Client client = this.clientService.connexion(pseudo, mdp);
            return ResponseEntity.ok("L'utilisateur " + client.getPseudo() + " est connecté ");
        }catch (ClientDejaConnecte e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Client" +pseudo+ "deja connecte");
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
        } catch (steam.serviceauth.exception.ClientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client pas inscrit");
        } catch (OperationNonAutorisee e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorize Operation");
        }
    }
    @PostMapping(value="/token")
    public ResponseEntity<String> creationToken(@RequestParam String pseudo,@RequestParam String mdp){
        try{
            String token= this.clientService.genererToken(pseudo,mdp);
            return ResponseEntity.status(HttpStatus.OK).header("auth_token",token).body("Token is in header");
        } catch (OperationNonAutorisee e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorize");
        } catch (ClientInexistantException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (UtilisateurPasInscritException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
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
