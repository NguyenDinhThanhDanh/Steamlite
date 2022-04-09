package steam.microclient.controller;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import steam.microcatalogue.Entities.Catalogue;
import steam.microclient.entities.Client;
import steam.microclient.exceptions.*;
import steam.microclient.service.ClientServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.springframework.http.HttpMethod.POST;

@RestController
@RequestMapping(value="client")
public class ClientController {
    @Autowired
    ClientServiceImpl clientService;
    private final static String URI_CATA="http://localhost:8080/catalogue/jeu/";
    private final static String URI_VENTE="http://localhost:8080/vente/client/";
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
    public ResponseEntity<String> connexion(@RequestBody Map<String,String> user,@RequestHeader(name="token") String token){
        String pseudo= user.get("pseudo");
        String mdp=user.get("mdp");
        try {
            Client client = this.clientService.connexion(pseudo,mdp,token);
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
    @DeleteMapping(value="desinscription/{id}")
        public ResponseEntity<String> desinscription(@PathVariable int id,@RequestBody Map<String,String> mdp) {
        try {
            String sMdp=mdp.get("mdp");
            this.clientService.desinscription(id, sMdp);
            return ResponseEntity.ok("compte supprime");
        }catch(MauvaisMdpException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mauvais mdp!");
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

    @GetMapping(value="/consultation/{pseudo}")
    public ResponseEntity<String> consultation(@PathVariable String pseudo) throws IOException, InterruptedException {
        HttpHeaders header= new HttpHeaders();
        String token= clientService.getToken(pseudo);

        header.setBearerAuth(token);
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest httpRequest=HttpRequest.newBuilder().uri(URI.create(URI_CATA)).header("token",token).GET().build();
        HttpResponse<String> response= httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok().body(response.body().toString());
        //System.out.println(response.body());

    }

    @GetMapping(value="/listJeux/{id}")
    public ResponseEntity<String> consultListJeux(@PathVariable int id) throws IOException, InterruptedException {
        Client client= clientService.getClientById(id);
        String pseudo=client.getPseudo();
        HttpHeaders header= new HttpHeaders();
        String token= clientService.getToken(pseudo);
        header.setBearerAuth(token);
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest httpRequest=HttpRequest.newBuilder().uri(URI.create(URI_VENTE+id)).header("token",token).GET().build();
        HttpResponse<String> response= httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok().body(response.body().toString());

    }

    @PostMapping(value="/achat/")
    public ResponseEntity<String> achatJeux(@RequestBody Map<String,String> bodyJeux){
        String idClient=bodyJeux.get("idClient");
        String idJeu=bodyJeux.get("idJeu");
        String prixAchat=bodyJeux.get("prixAchat");
        String dateAchat=bodyJeux.get("dateAchat");
        Client client= clientService.getClientById(Integer.parseInt(idClient));
        String pseudo=client.getPseudo();
        HttpHeaders header= new HttpHeaders();
        String token= clientService.getToken(pseudo);
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(token);
        RestTemplate restTemplate= new RestTemplate();
        JSONObject json=new JSONObject();
        json.put("idJeu",idJeu);
        json.put("idClient",idClient);
        json.put("prixAchat",prixAchat);
        json.put("dateAchat",dateAchat);
        System.out.println(json);
        HttpEntity<String> requestEntity= new HttpEntity<>(json.toString(),header);
        ResponseEntity<String> res=restTemplate.exchange("http://localhost:8080/vente/",POST,requestEntity,String.class);
        return ResponseEntity.ok().body(res.getBody().toString());
    }

    @PostMapping(value="/conversation/")
    public ResponseEntity<String> conversation(){

       return null;
    }

}
