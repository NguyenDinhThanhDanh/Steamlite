package steam.microclient.service;

import org.springframework.stereotype.Repository;
import steam.microclient.exceptions.ClientInexistantException;
import steam.microclient.exceptions.MauvaisTokenException;
import steam.microclient.exceptions.OperationNonAutorisee;
import steam.microclient.exceptions.PseudoDejaPrisException;
import steam.microclient.entities.Client;
import steam.microclient.exceptions.ClientDejaConnecte;
import steam.microclient.exceptions.IdClientUnknownException;
import steam.microclient.exceptions.UtilisateurPasInscritException;

import java.util.Collection;

@Repository
public interface ClientService {
    void createUtilisateur(String mdp,String pseudo,String dateInscrit) throws PseudoDejaPrisException;
    boolean verifUser(String pseudo,String mdp);
    Collection<Client> getAllUser();
    Client getUserById(int idUser) throws IdClientUnknownException;
    Client getUserByPseudo(String pseudo);
    Client connexion(String pseudo, String mdp) throws UtilisateurPasInscritException, ClientDejaConnecte;
    void deconnexion(Client client) throws ClientInexistantException, OperationNonAutorisee;

    Client getClientById(int idC);
}
