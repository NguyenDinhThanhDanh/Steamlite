package steam.microclient.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import steam.microclient.entities.Client;
import steam.microclient.exceptions.*;
import steam.microclient.repository.ClientRepository;

import javax.print.attribute.standard.Media;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.*;

@Service
public class ClientServiceImpl implements   ClientService{

    @Autowired
    private ClientRepository clientRepository;

    //private final static String URI_TOKEN_KEYCLOAK="http://localhost:8081/authent/token/";

    private HashMap<String, Client> clientsConnectes;
    private HashMap<String,String> ValuetoKey;
    public ClientServiceImpl() {

        this.clientsConnectes = new HashMap<>();
        this.ValuetoKey= new HashMap<>();
    }


    @Override
    public void createUser(Client client) throws PseudoDejaPrisException {
        if(verifUser(client.getPseudo(),client.getMdp())){
            throw new PseudoDejaPrisException();
        }
        clientRepository.save(client);
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
    public String checkToken(String token) throws MauvaisTokenException {
        if (clientsConnectes.containsKey(token)){
            return clientsConnectes.get(token).getPseudo();
        }
        else {
            throw new MauvaisTokenException();
        }
    }

    @Override
    public Client connexion(String pseudo,String mdp,String token) throws UtilisateurPasInscritException {


        Client client = clientRepository.findClientByPseudoAndAndMdp(pseudo,mdp);
        if(!this.verifUser(pseudo,mdp)){
            throw new UtilisateurPasInscritException();
        }

//        if(clientsConnectes.containsKey(pseudo)) throw new ClientDejaConnecte();
        if(this.verifUser(client.getPseudo(),client.getMdp())){
            if(!this.clientsConnectes.containsKey(token)){
                this.clientsConnectes.put(token,client);
                this.ValuetoKey.put(client.toString(),token);
            }
        }
        System.out.println(clientsConnectes.toString());
        return client;

    }

    @Override
    public void deconnexion(Client client) throws OperationNonAutorisee {
        if(this.verifUser(client.getPseudo(),client.getMdp())){
            this.clientsConnectes.remove(client.getPseudo());
        }
        else{
            throw new OperationNonAutorisee();
        }
    }

    @Override
    public void desinscription(int id, String mdp) throws MauvaisMdpException {
        Client client=getClientById(id);
        if(!client.getMdp().equals(mdp)){
            throw new MauvaisMdpException();
        }else{
            this.clientRepository.deleteById(id);
        }
    }

    @Override
    public Client getClientById(int idC) {
        return this.clientRepository.findById(idC).get();
    }

    @Override
    public String getToken(String pseudo) {
        Client client = getUserByPseudo(pseudo);
        System.out.println(client.toString());
        String token = ValuetoKey.get(client.toString());
        System.out.println(token);
        return token;
    }

//    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
//        for (Map.Entry<T, E> entry : map.entrySet()) {
//            if (Objects.equals(value, entry.getValue())) {
//                return entry.getKey();
//            }
//        }
//        return null;
//    }



}
