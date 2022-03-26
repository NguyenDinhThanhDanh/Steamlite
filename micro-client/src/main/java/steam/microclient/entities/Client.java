package steam.microclient.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idC;
    private String pseudo;
    private String mdp;
    private String dateInscrit;

    public Client() {
    }

    public String getDateInscrit() {
        return dateInscrit;
    }

    public void setDateInscrit(String dateInscrit) {
        this.dateInscrit = dateInscrit;
    }



    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }



    public Client(String pseudo, String mdp, String dateInscrit){
        this.pseudo= pseudo;
        this.mdp=mdp;
        this.dateInscrit= dateInscrit;
    }

    @Override
    public String toString(){
        return "Client{"+ "id="+idC + ",pseudo="+ pseudo
                +",motDePasse="+mdp+",dateInscrit="+  dateInscrit + "}";


    }

    public boolean checkPasswordClient(String mdpClient) {
        return this.mdp.equals(mdpClient);
    }
}
