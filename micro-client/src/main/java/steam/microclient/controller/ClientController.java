package steam.microclient.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import steam.microclient.entities.Client;
import steam.microclient.exceptions.*;
import steam.microclient.service.ClientServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


@RestController
@RequestMapping(value="client")
public class ClientController {
    @Autowired
    ClientServiceImpl clientService;
    private final static String URI_CATA="http://localhost:8080/catalogue/jeu/";
    private final static String URI_VENTE="http://localhost:8080/vente/client/";
    private final static String URI_SOCIAL="http://localhost:8080/social/message";
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
        try {
            HttpRequest httpRequest=HttpRequest.newBuilder().uri(URI.create(URI_VENTE+id)).header("token",token).GET().build();
            HttpResponse<String> response= httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
            return ResponseEntity.ok().body(response.body().toString());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id de client inconnu");
        }
    }

    @PostMapping(value="/achat/")
    public ResponseEntity<String> achatJeux(@RequestBody Map<String,String> bodyJeux){
        String idClient = bodyJeux.get("idClient");
        String idJeu = bodyJeux.get("idJeu");
        String prixAchat = bodyJeux.get("prixAchat");
        String dateAchat = bodyJeux.get("dateAchat");
        System.out.println(idClient + " "+ idJeu+ " " + prixAchat+ " " + dateAchat);

        String token= clientService.getToken(clientService.getClientById(Integer.parseInt(idClient)).getPseudo());
        System.out.println(token);

        JSONObject json = new JSONObject();
        json.put("idJeu",idJeu);
        json.put("idClient",idClient);
        json.put("prixAchat",prixAchat);
        json.put("dateAchat",dateAchat);
        System.out.println(json);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/vente/"))
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .header("token", token)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            return ResponseEntity.status(HttpStatus.CREATED).body( idClient + " a bien acheté le jeu " + idJeu);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("La requete n'est pas bonne");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Une erreure est servenue");
        }
    }

    @PostMapping(value="/tchat/")
    public ResponseEntity<String> tchatEnvoyer(@RequestBody Map<String,String> bodyMessage){
        String receveur = bodyMessage.get("receveur");
        String envoyeur = bodyMessage.get("envoyeur");
        String dateChat = bodyMessage.get("dateChat");
        String message = bodyMessage.get("message");
        System.out.println(receveur + " "+ envoyeur+ " " + dateChat+ " " + message);

        String token = clientService.getToken(clientService.getClientById(Integer.parseInt(envoyeur)).getPseudo());
        System.out.println(token);

        JSONObject json = new JSONObject();
        json.put("receveur",receveur);
        json.put("envoyeur",envoyeur);
        json.put("dateChat",dateChat);
        json.put("message",message);
        System.out.println(json);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URI_SOCIAL))
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .header("token", token)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            return ResponseEntity.status(HttpStatus.CREATED).body( receveur + " a bien envoyé le message " + message + " à " + receveur);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("La requete n'est pas bonne");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Une erreure est servenue");
        }
    }

    @GetMapping(value="/tchat/{id}")
    public ResponseEntity<String> tchatRecevoir(){

        return null;
    }

    @GetMapping(value="/tchat/{id}/{id2}")
    public ResponseEntity<String> tchatRecevoirEchange(){

        return null;
    }

}