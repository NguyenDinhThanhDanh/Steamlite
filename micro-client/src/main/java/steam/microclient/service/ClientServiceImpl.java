package steam.microclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microclient.entities.Client;
import steam.microclient.exceptions.*;
import steam.microclient.repository.ClientRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class ClientServiceImpl implements   ClientService{

    @Autowired
    private ClientRepository clientRepository;

    private HashMap<String, Client> clientsConnectes;

    public ClientServiceImpl(HashMap<String, Client> clientsConnectes) {
        this.clientsConnectes = clientsConnectes;
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
    public Client connexion(String pseudo, String mdp) throws UtilisateurPasInscritException {
        String uri="http://localhost:8081/authent/inscription/";
        String token="";
        Client client = clientRepository.findClientByPseudoAndAndMdp(pseudo,mdp);
        if(!this.verifUser(pseudo,mdp)){
            throw new UtilisateurPasInscritException();
        }
//        if(clientsConnectes.containsKey(pseudo)) throw new ClientDejaConnecte();
        if(this.verifUser(client.getPseudo(),client.getMdp())){
            if(!this.clientsConnectes.containsKey(client)){
                this.clientsConnectes.put(token,client);
            }
        }
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
    public Client getClientById(int idC) {
        return this.clientRepository.findById(idC).get();
    }
}
