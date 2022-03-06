package steam.serviceauth.modele;

import steam.microclient.exceptions.*;

public interface FacadeClient {

    void inscription(String nomClient, String mdpClient) throws PseudoDejaPrisException, MotDePasseInvalideException;

    String genererToken(String nomClient, String mdpClient) throws JoueurInexistantException, OperationNonAutorisee;

    String checkToken(String token) throws MauvaisTokenException;

}
