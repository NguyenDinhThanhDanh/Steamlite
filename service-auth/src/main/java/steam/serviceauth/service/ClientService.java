package steam.serviceauth.service;

import org.springframework.stereotype.Repository;
import steam.microclient.exceptions.ClientInexistantException;
import steam.microclient.exceptions.MauvaisTokenException;
import steam.microclient.exceptions.OperationNonAutorisee;
import steam.microclient.exceptions.PseudoDejaPrisException;
import steam.serviceauth.entities.Client;
import steam.serviceauth.exception.ClientDejaConnecte;
import steam.serviceauth.exception.IdClientUnknownException;
import steam.serviceauth.exception.UtilisateurPasInscritException;

import java.util.Collection;

@Repository
public interface ClientService {
    void createUtilisateur(String mdp,String pseudo,String dateInscrit) throws PseudoDejaPrisException;
    boolean verifUser(String pseudo,String mdp);
    Collection<Client> getAllUser();
    Client getUserById(int idUser) throws IdClientUnknownException;
    Client getUserByPseudo(String pseudo);
    String genererToken(String nomClient, String mdpClient) throws ClientInexistantException, OperationNonAutorisee, UtilisateurPasInscritException;
    String checkToken(String token) throws MauvaisTokenException;
    Client connexion(String pseudo, String mdp) throws UtilisateurPasInscritException, ClientDejaConnecte;
    void deconnexion(Client client) throws steam.serviceauth.exception.ClientInexistantException, OperationNonAutorisee;

    Client getClientById(int idC);
}
