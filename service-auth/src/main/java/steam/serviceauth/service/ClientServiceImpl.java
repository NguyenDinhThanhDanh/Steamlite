package steam.serviceauth.service;

import net.bytebuddy.dynamic.scaffold.MethodGraph;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import steam.serviceauth.exception.*;

import steam.serviceauth.entities.Client;
import steam.serviceauth.repository.ClientRepository;
import steam.serviceauth.service.ClientService;

import java.net.http.HttpResponse;
import java.util.*;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class ClientServiceImpl implements ClientService {
    private String client_id="steamlite-web";
    private String value="steam";
    @Autowired
    private ClientRepository clientRepository;

    private HashMap<String,Client> clientsConnectes;

    public ClientServiceImpl(){
        this.clientsConnectes= new HashMap<>();
    }

    @Override
    public void createUtilisateur(Client client) throws PseudoDejaPrisException {
        String pseudo=client.getPseudo();
        String mdp=client.getMdp();
        String email=client.getEmail();
        System.out.println(pseudo);
        if(this.verifUser(pseudo,mdp)){
            throw new PseudoDejaPrisException();
        }
        else if(Objects.nonNull(client)){
            HttpHeaders header= new HttpHeaders();
            header.setBearerAuth(this.keycloakToken(pseudo,mdp));
            RestTemplate restTemplate = new RestTemplate();
            header.setContentType(MediaType.APPLICATION_JSON);
            JSONObject json= new JSONObject();
            json.put("email",email);
            json.put("username",pseudo);
            json.put("emailVerified",true);
            json.put("enabled",true);




//            json.put("firstname","");
//            json.put("lastname","");

            System.out.println(json.toString());
           HttpEntity<String> requesteEntity= new HttpEntity<>(json.toString(),header);
            ResponseEntity<String> res   =restTemplate.exchange("http://localhost:8000/auth/admin/realms/steam",POST,requesteEntity,String.class);
            System.out.println(res);
            clientRepository.save(client);
//            ResponseEntity<String> res   =restTemplate.exchange("http://localhost:8000/auth/admin/steam",POST,requesteEntity,String.class);
        }

    }

    @Override
    public boolean verifUser(String pseudo, String mdp) {
        if(clientRepository.findClientByPseudoAndAndMdp(pseudo,mdp)==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Collection<Client> getAllUser() {
        Collection<Client> clients= new ArrayList<>();
        clientRepository.findAll().forEach(clients::add);
        return clients;
    }

    @Override
    public Client getUserById(int idUser) throws IdClientUnknownException {
        if(!clientRepository.existsById(idUser)){
            throw new IdClientUnknownException();
        }
        Client client =  this.clientRepository.findById(idUser).get();
        return client;
    }

    @Override
    public Client getUserByPseudo(String pseudo) {
        return clientRepository.findClientByPseudo(pseudo);
    }

    @Override
    public String genererToken(String nomClient, String mdpClient) throws JoueurInexistantException, OperationNonAutorisee, UtilisateurPasInscritException {
        if (!this.verifUser(nomClient,mdpClient))
            throw new JoueurInexistantException();

        Client client = this.getUserByPseudo(nomClient);
        System.out.println(client.getMdp());
        if (client.checkPasswordClient(client.getMdp())) {
            String idConnection = UUID.randomUUID().toString();
            this.clientsConnectes.put(idConnection,client);
            System.out.println(idConnection);
            return idConnection;
        }
        else {
            throw new OperationNonAutorisee();
        }
    }

    @Override
    public String checkToken(String token) throws MauvaisTokenException {
        if (clientsConnectes.containsKey(token)){
            return clientsConnectes.get(token).getPseudo();
        }
        else {
            throw new MauvaisTokenException();
        }
    }


    public Client connexion(String pseudo, String mdp) throws ClientDejaConnecte, UtilisateurPasInscritException {
        Client client = clientRepository.findClientByPseudoAndAndMdp(pseudo,mdp);
        if(!this.verifUser(pseudo,mdp)){
            throw new UtilisateurPasInscritException();
        }
        if(clientsConnectes.containsKey(pseudo)) throw new ClientDejaConnecte();
        if(this.verifUser(client.getPseudo(),client.getMdp())){
            if(!this.clientsConnectes.containsKey(client)){
                this.clientsConnectes.put(pseudo,client);
            }
        }
        return client;
    }

    @Override
    public void deconnexion(Client client) throws ClientInexistantException, OperationNonAutorisee {
        if(this.verifUser(client.getPseudo(),client.getMdp())){
            if(!clientsConnectes.containsKey(client.getPseudo()))
                throw new ClientInexistantException();
            this.clientsConnectes.remove(client.getPseudo());
        }
        else{
            throw new OperationNonAutorisee();
        }

    }

    @Override
    public Client getClientById(int idC) {
        return this.clientRepository.findById(idC).get();

    }

    @Override
    public String keycloakToken(String username, String password) {
        username="admin";
        password="admin";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("client_id",client_id);
        map.add("username",username);
        map.add("password",password);
        map.add("grant_type","password");
        RestTemplate restTemplate= new RestTemplate();
        HttpEntity<MultiValueMap<String,String>> httpEntity= new HttpEntity<>(map,header);
        ResponseEntity<String> response= restTemplate.exchange("http://localhost:8000/auth/realms/steam/protocol/openid-connect/token",POST,
                httpEntity,String.class);
        String token=response.getBody().split(",")[0].split(":")[1].split("\"")[1];
        return token;
    }



}
