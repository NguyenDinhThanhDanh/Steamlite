package steam.microclient.modele;

public class Client {

    private String nomClient;
    private String mdpClient;

    public Client(String nomClient, String mdpClient) {
        this.nomClient = nomClient;
        this.mdpClient = mdpClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public boolean checkPasswordClient(String mdp){
        return this.mdpClient.equals(mdp);
    }

}
