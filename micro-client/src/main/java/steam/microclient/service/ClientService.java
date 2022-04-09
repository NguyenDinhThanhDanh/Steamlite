package steam.microclient.service;

import steam.microclient.entities.Client;
import steam.microclient.exceptions.*;

import java.util.Collection;

public interface ClientService {
    void createUser(Client client) throws PseudoDejaPrisException;
    boolean verifUser(String pseudo,String mdp);
    Collection<Client> getAllUser();
    Client getUserById(int idUser) throws IdClientUnknownException;
    Client getUserByPseudo(String pseudo);
    String checkToken(String token) throws MauvaisTokenException;
    Client connexion(String pseudo,String mdp,String token) throws UtilisateurPasInscritException;
    void deconnexion(Client client) throws OperationNonAutorisee;
    void desinscription(int id,String mdp) throws MauvaisMdpException;
    Client getClientById(int idC);
    String getToken(String pseudo);
    String execAchat(int idClient,String idJeu,String dateAchat,int prixAchat);
}
