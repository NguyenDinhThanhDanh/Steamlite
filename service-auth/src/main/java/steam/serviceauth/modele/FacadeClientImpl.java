package steam.serviceauth.modele;

import org.springframework.stereotype.Component;
import steam.microclient.exceptions.*;
import steam.microclient.modele.Client;
import steam.serviceauth.dao.MysqlClient;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component("facadeClient")
public class FacadeClientImpl implements FacadeClient{

    private Map<String, Client> clients;
    private Map<String, Client> clientsConnectes;
    Connection connection = MysqlClient.getConnection();
    public FacadeClientImpl()  {
//        this.clients = new HashMap<>();
//        this.clientsConnectes = new HashMap<>();
    }

    @Override
    public void inscription(String nomClient, String mdpClient) throws PseudoDejaPrisException, MotDePasseInvalideException {
        String query = "INSERT INTO mysql-client() ";
    }

    @Override
    public String genererToken(String nomClient, String mdpClient) throws JoueurInexistantException, OperationNonAutorisee {
        if (!clients.containsKey(nomClient))
            throw new JoueurInexistantException();

        Client client = clients.get(nomClient);
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
            return clientsConnectes.get(token).getNomClient();
        }
        else {
            throw new MauvaisTokenException();
        }
    }
}
