package steam.serviceauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.serviceauth.entities.ClientKC;
import steam.serviceauth.service.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping(value = "/authent")
public class ControleurAuthent {

    @Autowired
    AuthentServiceImpl clientService;

    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscription(@RequestBody ClientKC client) {
        this.clientService.createUtilisateur(client);
        return ResponseEntity.ok("Le compte a bien été crée");
    }
//    @PostMapping(value="/connexion")
//    public ResponseEntity<String> connexion(@RequestParam String pseudo,@RequestParam String mdp){
//        try {
//            Client client = this.clientService.connexion(pseudo, mdp);
//            return ResponseEntity.ok("L'utilisateur " + client.getPseudo() + " est connecté ");
//        }catch (ClientDejaConnecte e){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Client" +pseudo+ "deja connecte");
//        }catch (UtilisateurPasInscritException e){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client "+pseudo+ " n'existe pas dans steam");
//        }
//    }
//    @PostMapping(value="/{idC}/deconnexion")
//    public ResponseEntity<String> deconnexion(@PathVariable int idC){
//        try{
//            Client client = this.clientService.getClientById(idC);
//            this.clientService.deconnexion(client);
//            return ResponseEntity.created(URI.create("/deconnexion")).body("L'utilisateur "+client.getPseudo() + " est déconnecté ");
//        } catch (ClientInexistantException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client pas inscrit");
//        } catch (OperationNonAutorisee e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorize Operation");
//        }
//    }
    @PostMapping(value="/token")
    public ResponseEntity<String> creationToken(@RequestBody Map<String,String> user){
        String pseudo=user.get("pseudo");
        String mdp=user.get("mdp");
        String token= this.clientService.keycloakToken(pseudo,mdp);
        return ResponseEntity.status(HttpStatus.OK).header("token",token).body("Token is in header");
    }

    @PostMapping(value = "/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String,String> credential) throws SQLException {
        String pseudo=credential.get("pseudo");
        String mdp=credential.get("mdp");
        this.clientService.setUserPassWord(pseudo,mdp);
        return ResponseEntity.status(HttpStatus.OK).body("Password set successfully");
    }


//    @GetMapping(value = "/token")
//    public ResponseEntity<String> checkToken(@RequestParam String token){
//        try {
//            return ResponseEntity.ok(this.clientService.checkToken(token));
//        } catch (MauvaisTokenException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }




}
