package steam.microsocial.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(collection = "Social")
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSocial;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idChat;
    private int idJoueur1;
    private int idJoueur2;
    private String dateChat;
    private String message;

    public Social(int idSocial,int idChat, int idJoueur1, int idJoueur2, String dateChat, String message) {
        this.idSocial = idSocial;
        this.idChat = idChat;
        this.idJoueur1 = idJoueur1;
        this.idJoueur2 = idJoueur2;
        this.dateChat = dateChat;
        this.message = message;

    }

    public int getIdSocial() {
        return idSocial;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public int getIdJoueur1() {
        return idJoueur1;
    }

    public void setIdJoueur1(int idJoueur1) {
        this.idJoueur1 = idJoueur1;
    }

    public int getIdJoueur2() {
        return idJoueur2;
    }

    public void setIdJoueur2(int idJoueur2) {
        this.idJoueur2 = idJoueur2;
    }

    public String getDateChat() {
        return dateChat;
    }

    public void setDateChat(String dateChat) {
        this.dateChat = dateChat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
