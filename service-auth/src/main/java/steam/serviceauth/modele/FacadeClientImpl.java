package steam.serviceauth.modele;

import org.springframework.stereotype.Component;
import steam.microclient.exceptions.*;
import steam.microclient.modele.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component("facadeClient")
public class FacadeClientImpl implements FacadeClient{

    private Map<String, Client> clients;
    private Map<String, Client> clientsConnectes;

    public FacadeClientImpl() {
        this.clients = new HashMap<>();
        this.clientsConnectes = new HashMap<>();
    }

    @Override
    public void inscription(String nomClient, String mdpClient) throws PseudoDejaPrisException, MotDePasseInvalideException {
        if (clients.containsKey(nomClient))
            throw new PseudoDejaPrisException();

        if (mdpClient.length() < 5)
            throw new MotDePasseInvalideException();

        this.clients.put(nomClient, new Client(nomClient, mdpClient));
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
