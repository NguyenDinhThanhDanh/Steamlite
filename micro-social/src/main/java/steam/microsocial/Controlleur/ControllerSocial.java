package steam.microsocial.Controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import steam.microsocial.Entities.Message;
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
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            System.out.println("iciiii");
            serviceSocial.sendNewMessage(message);
                return ResponseEntity.created(URI.create("/social/message/" + message.getIdMessage())).body("Votre message << " + message.getIdMessage() + " >> a bien été envoyé à " + message.getReceveur());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @GetMapping(value = "/message/{idMessage}")
    public ResponseEntity<Social> getMessageJoueur(@PathVariable Integer idMessage){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            Social social = serviceSocial.getSocial(idMessage);
            ResponseEntity<Social> responseEntity = ResponseEntity.ok(social);
            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
