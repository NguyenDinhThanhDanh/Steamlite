package steam.serviceauth.modele;

import steam.microclient.exceptions.*;
import steam.serviceauth.client.Client;
import steam.serviceauth.exception.ClientInexistantException;
import steam.serviceauth.exception.UtilisateurPasInscritException;

import java.time.LocalDate;

public interface FacadeClient {

    long inscription(String nomClient, String mdpClient, LocalDate dateInscrit) throws PseudoDejaPrisException, MotDePasseInvalideException;

    String genererToken(String nomClient, String mdpClient) throws JoueurInexistantException, OperationNonAutorisee, UtilisateurPasInscritException;

    String checkToken(String token) throws MauvaisTokenException;

    Client connexion(String pseudo, String mdp) throws UtilisateurPasInscritException, OperationNonAutorisee, JoueurInexistantException;

    Client getClientWithPseudoAndMdp(String pseudo,String mdp);

    Client getClientById(int idC);

    void deconnexion(Client client) throws ClientInexistantException, OperationNonAutorisee;
}
