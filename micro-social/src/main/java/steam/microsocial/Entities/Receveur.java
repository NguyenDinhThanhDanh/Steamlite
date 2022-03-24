package steam.microsocial.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "receveur")
public class Receveur {

    @Id
    private int idReception;
    private int receveur;
    private List<Message> messages;


    public Receveur(int idReception, List<Message> messages, int receveur) {
        this.idReception = idReception;
        this.messages = messages;
        this.receveur = receveur;
    }

    public int getIdReception() {
        return idReception;
    }

    public void setIdReception(int idReception) {
        this.idReception = idReception;
    }

    public int getReceveur() {
        return receveur;
    }

    public void setReceveur(int receveur) {
        this.receveur = receveur;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
