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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

@RestController
@RequestMapping(value = "/social",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControllerSocial {
    @Autowired
    private ServiceSocial serviceSocial;

    private final static String URI_CLIENT= "http://localhost:8080/client/token?token=";

    @PostMapping(value = "/message")
    public ResponseEntity<String> addMessage(@RequestBody Message message, @RequestHeader(name="token") String token){
        if (checkToken(token))
        {
            try{
                serviceSocial.sendNewMessage(message);
                return ResponseEntity.created(URI.create("/social/message/" + message.getIdMessage())).body("Votre message a bien été envoyé à " + message.getReceveur());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/message/{id}")
    public ResponseEntity<Social> getMessageJoueurEnvoye(@PathVariable int id, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Social social = serviceSocial.getSocialById(id);
                ResponseEntity<Social> responseEntity = ResponseEntity.ok(social);
                return responseEntity;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/message/{id}/{id2}")
    public ResponseEntity<Collection<Message>> getMessagesJoueurToJoueur(@PathVariable int id, @PathVariable int id2, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Collection<Message> messages = serviceSocial.getSocialByIdWithId(id, id2);
                ResponseEntity<Collection<Message>> responseEntity = ResponseEntity.ok(messages);
                return responseEntity;
            } catch (UnknownEnvoyeurException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/message")
    public ResponseEntity<Collection<Social>> getMessageJoueurAll(@RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Collection<Social> social = serviceSocial.getSocialAll();
                ResponseEntity<Collection<Social>> responseEntity = ResponseEntity.ok(social);
                return responseEntity;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(value = "/recevoir")
    public ResponseEntity<Collection<Receveur>> getMessageJoueurRecuAll(@RequestHeader(name="token") String token){
        if (checkToken(token)){
            try{
                Collection<Receveur> receveur = serviceSocial.getRecveurAll();
                ResponseEntity<Collection<Receveur>> responseEntity = ResponseEntity.ok(receveur);
                return responseEntity;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping(value = "/message/{idMessage}/{idEnvoyeur}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer idEnvoyeur, @PathVariable Integer idMessage, @RequestHeader(name="token") String token){
        if (checkToken(token)){
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
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping(value = "/{idSocial}")
    public ResponseEntity<String> deleteSocialById(@PathVariable Integer idSocial, @RequestHeader(name="token") String token){
        if (checkToken(token)){
            try {
                serviceSocial.deleteSocial(idSocial);
                return ResponseEntity.created(URI.create("/social/message/" + idSocial)).body("Votre message << " + idSocial + " >> a bien été supprimé");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
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
