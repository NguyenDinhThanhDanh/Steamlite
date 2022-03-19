package steam.microsocial.Controlleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositorySocial;
import steam.microsocial.Service.ServiceSocial;

import java.net.URI;

@RestController
@RequestMapping(value = "/social",produces = {MediaType.APPLICATION_JSON_VALUE})
public class ControllerSocial {
    @Autowired
    private ServiceSocial serviceSocial;

    @PostMapping(value = "/message")
    public ResponseEntity<String> addMessage(@RequestBody Social social){
/*      HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URI_Catalogue+"?token="+token)).GET().build();*/
        try{
            serviceSocial.sendNewMessage(social);
                return ResponseEntity.created(URI.create("/social/message/" + social.getIdSocial())).body("Votre message << " + social.getMessage() + " >> a bien été envoyé à " + social.getIdJoueur2());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
}
