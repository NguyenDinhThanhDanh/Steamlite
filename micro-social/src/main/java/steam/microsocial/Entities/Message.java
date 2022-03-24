package steam.microsocial.Entities;

import javax.persistence.Id;

public class Message {
    @Id
    private int idMessage;
    private int receveur;
    private int envoyeur;
    private String dateChat;
    private String message;

    public Message(int idMessage, int receveur,int envoyeur, String dateChat, String message){
        this.idMessage = idMessage;
        this.receveur = receveur;
        this.envoyeur = envoyeur;
        this.dateChat = dateChat;
        this.message = message;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getReceveur() {
        return receveur;
    }

    public void setReceveur(int receveur) {
        this.receveur = receveur;
    }

    public int getEnvoyeur() {
        return envoyeur;
    }

    public void setEnvoyeur(int envoyeur) {
        this.envoyeur = envoyeur;
    }

    public int getIdJoueur2() {
        return receveur;
    }

    public void setIdJoueur2(int idJoueur2) {
        this.receveur = idJoueur2;
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
