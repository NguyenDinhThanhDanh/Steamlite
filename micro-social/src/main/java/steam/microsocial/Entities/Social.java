package steam.microsocial.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "social")
public class Social {

    @Id
    private int idSocial;
    private int envoyeur;
    private List<Message> messages;


    public Social(int idSocial, List<Message> messages, int envoyeur) {
        this.idSocial = idSocial;
        this.messages = messages;
        this.envoyeur = envoyeur;
    }

    public int getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(int envoyeur) {
        this.envoyeur = envoyeur;
    }

    public int getIdSocial() {
        return idSocial;
    }

    public void setIdSocial(int idSocial) {
        this.idSocial = idSocial;
    }

    public List<Message> getListeMessage() {
        return messages;
    }

    public void setListeMessage(List<Message> listeMessage) {
        this.messages = listeMessage;
    }
}
