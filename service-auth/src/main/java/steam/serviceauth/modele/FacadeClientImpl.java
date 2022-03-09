package steam.serviceauth.modele;

import org.springframework.stereotype.Component;
import steam.microclient.exceptions.*;

import steam.serviceauth.client.Client;
import steam.serviceauth.dao.MysqlClient;
import steam.serviceauth.exception.UtilisateurPasInscritException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component("facadeClient")
public class FacadeClientImpl implements FacadeClient{


    private Map<String, Client> clientsConnectes;
    private MysqlClient mysqlClient;
    public FacadeClientImpl()  {
//        this.clients = new HashMap<>();
//        this.clientsConnectes = new HashMap<>();
    }

    @Override
    public long inscription(String nomClient, String mdpClient, LocalDate dateInscrit) throws MotDePasseInvalideException, PseudoDejaPrisException {
        if (this.mysqlClient.verifUser(nomClient, mdpClient)) {
            throw new PseudoDejaPrisException();
        }else {
            return mysqlClient.createUtilisateur(nomClient,mdpClient,dateInscrit.toString());
        }
    }

    @Override
    public String genererToken(String nomClient, String mdpClient) throws JoueurInexistantException, OperationNonAutorisee, UtilisateurPasInscritException {
        if (!this.mysqlClient.verifUser(nomClient,mdpClient))
            throw new JoueurInexistantException();

        Client client = mysqlClient.getUserByPseudo(nomClient);
        if (client.checkPasswordClient(mdpClient)) {
            String idConnection = UUID.randomUUID().toString();
            this.clientsConnectes.put(idConnection, client);
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
}
