package steam.serviceauth.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import steam.microclient.exceptions.*;
import steam.serviceauth.modele.FacadeClient;

@RestController
@RequestMapping(value = "/authent")
public class ControleurClients {

    @Autowired
    FacadeClient facadeClient;

    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscription(@RequestParam String pseudo, @RequestParam String mdp) {
        try {
            this.facadeClient.inscription(pseudo,mdp);
            return ResponseEntity.ok("Le compte a bien été crée");
        } catch (PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pseudo "+pseudo+" déjà pris");
        } catch (MotDePasseInvalideException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mot de passe doit contenir au moins 5 caractères");
        }
    }

}
