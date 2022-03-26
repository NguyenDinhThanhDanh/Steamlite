package steam.microclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microclient.exceptions.*;
import steam.microclient.entities.Client;
import steam.microclient.service.ClientServiceImpl;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/client")
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
        } catch (ClientInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client pas inscrit");
        } catch (OperationNonAutorisee e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorize Operation");
        }
    }
}