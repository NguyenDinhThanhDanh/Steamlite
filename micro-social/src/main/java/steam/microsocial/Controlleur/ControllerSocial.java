package steam.microsocial.Controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microcatalogue.Exception.UnknownEnvoyeurException;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositorySocial;
import steam.microsocial.Service.ServiceSocial;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping(value = "/social",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControllerSocial {
    @Autowired
    private ServiceSocial serviceSocial;

    @PostMapping(value = "/message")
    public ResponseEntity<String> addMessage(@RequestBody Message message){
     /* HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            serviceSocial.sendNewMessage(message);
            return ResponseEntity.created(URI.create("/social/message/" + message.getIdMessage())).body("Votre message a bien été envoyé à " + message.getReceveur());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @GetMapping(value = "/message/{id}")
    public ResponseEntity<Collection<Social>> getMessageJoueurEnvoye(@PathVariable int id){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            Collection<Social> social = serviceSocial.getSocialAll();
            ResponseEntity<Collection<Social>> responseEntity = ResponseEntity.ok(social);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @GetMapping(value = "/message")
    public ResponseEntity<Collection<Social>> getMessageJoueurAll(){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            Collection<Social> social = serviceSocial.getSocialAll();
            ResponseEntity<Collection<Social>> responseEntity = ResponseEntity.ok(social);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @GetMapping(value = "/recevoir")
    public ResponseEntity<Collection<Receveur>> getMessageJoueurRecuAll(){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            Collection<Receveur> receveur = serviceSocial.getRecveurAll();
            ResponseEntity<Collection<Receveur>> responseEntity = ResponseEntity.ok(receveur);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @DeleteMapping(value = "/message/{idMessage}/{idEnvoyeur}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer idEnvoyeur, @PathVariable Integer idMessage){
        try{
            System.out.println("id envoyeur : " + idEnvoyeur);
            System.out.println("id envoyeur : " + idMessage);
            serviceSocial.deleteMessage(idEnvoyeur, idMessage);
            return ResponseEntity.created(URI.create("/social/message/" + idMessage)).body("Votre message << " + idMessage + " >> a bien été supprimé");
        } catch (UnknownIdMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le message passé est incorrect ou n'existe pas");
        } catch (UnknownEnvoyeurException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("vous n'etes pas connecté");
        }
    }

    @DeleteMapping(value = "/{idSocial}")
    public ResponseEntity<String> deleteSocialById(@PathVariable Integer idSocial){
        try {
            serviceSocial.deleteSocial(idSocial);
            return ResponseEntity.created(URI.create("/social/message/" + idSocial)).body("Votre message << " + idSocial + " >> a bien été supprimé");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
