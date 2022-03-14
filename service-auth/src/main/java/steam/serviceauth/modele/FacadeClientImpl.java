package steam.serviceauth.modele;

import org.springframework.stereotype.Component;
import steam.microclient.exceptions.*;

import steam.serviceauth.client.AES;
import steam.serviceauth.client.Client;
import steam.serviceauth.dao.MysqlClient;
import steam.serviceauth.exception.ClientInexistantException;
import steam.serviceauth.exception.UtilisateurPasInscritException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component("facadeClient")
public class FacadeClientImpl implements FacadeClient{


    private HashMap<String,Client> clientsConnectes;
    private MysqlClient mysqlClient;
    private Collection<Client> clientInscirts;
    private static FacadeClient instance;
    public FacadeClientImpl() throws Exception {
        this.instance=this;
        this.mysqlClient=new MysqlClient();
        this.clientsConnectes = new HashMap<>();
        this.clientInscirts = this.mysqlClient.getAllUsers();

    }
    public static FacadeClient getInstance() throws Exception {
        if(instance==null){
            instance=new FacadeClientImpl();
        }
        return instance;
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
            this.clientsConnectes.put(idConnection,client);
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
    @Override
    public Client connexion(String pseudo,String mdp) throws OperationNonAutorisee, JoueurInexistantException, UtilisateurPasInscritException {
        Client client = this.getClientWithPseudoAndMdp(pseudo,mdp);
        if(mysqlClient.verifUser(client.getPseudo(),AES.decrypt(client.getMdp(),pseudo))){
            if(!this.clientsConnectes.containsKey(client)){
                this.clientsConnectes.put(pseudo,client);
            }
        }
        return client;
    }

    @Override
    public void deconnexion(Client client) throws ClientInexistantException, OperationNonAutorisee {
        if(this.mysqlClient.verifUser(client.getPseudo(),client.getMdp())){
            if(!clientsConnectes.containsKey(client.getPseudo()))
                throw new ClientInexistantException();
                this.clientsConnectes.remove(client.getPseudo());
            }
        else{
            throw new OperationNonAutorisee();
        }

    }



    @Override
    public Client getClientWithPseudoAndMdp(String pseudo, String mdp) {
        for(Client client: this.clientInscirts){
            if(client.getPseudo().equals(pseudo) && client.getMdp().equals(AES.encrypt(mdp,pseudo))){
                return client;
            }
        }
        return null;
    }

    @Override
    public Client getClientById(int idC) {
        return mysqlClient.getUserById(idC);
    }
}
