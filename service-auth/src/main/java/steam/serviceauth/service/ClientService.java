package steam.serviceauth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import steam.serviceauth.exception.*;
import steam.serviceauth.entities.Client;
import steam.serviceauth.exception.*;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ClientService {
    void createUtilisateur(Client client) throws PseudoDejaPrisException;
    boolean verifUser(String pseudo,String mdp);
    Collection<Client> getAllUser();
    Client getUserById(int idUser) throws IdClientUnknownException;
    Client getUserByPseudo(String pseudo);
    String genererToken(String nomClient, String mdpClient) throws JoueurInexistantException, OperationNonAutorisee, UtilisateurPasInscritException;
    String checkToken(String token) throws MauvaisTokenException;
    Client connexion(String pseudo, String mdp) throws UtilisateurPasInscritException, ClientDejaConnecte;
    void deconnexion(Client client) throws ClientInexistantException, OperationNonAutorisee;

    Client getClientById(int idC);
    //ResponseEntity<String> userKeyCloak();
    String keycloakToken(String username,String password);
}
