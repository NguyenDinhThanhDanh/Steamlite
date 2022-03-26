package steam.microclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microclient.exceptions.ClientInexistantException;
import steam.microclient.exceptions.MauvaisTokenException;
import steam.microclient.exceptions.OperationNonAutorisee;
import steam.microclient.exceptions.PseudoDejaPrisException;
import steam.microclient.entities.Client;
import steam.microclient.repository.ClientRepository;
import steam.microclient.exceptions.ClientDejaConnecte;
import steam.microclient.exceptions.IdClientUnknownException;
import steam.microclient.exceptions.UtilisateurPasInscritException;

import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

       @Autowired
   private ClientRepository clientRepository;

       private HashMap<String,Client> clientsConnectes;

    public ClientServiceImpl(){
        this.clientsConnectes= new HashMap<>();
    }

    @Override
    public void createUtilisateur(String mdp, String pseudo, String dateInscrit) throws PseudoDejaPrisException {
        Client client= new Client(pseudo, mdp, dateInscrit);
        if(this.verifUser(pseudo,mdp)){
            throw new PseudoDejaPrisException();
        }
        else if(Objects.nonNull(client)){
            clientRepository.save(client);
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
}
