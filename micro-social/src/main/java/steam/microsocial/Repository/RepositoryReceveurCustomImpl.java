package steam.microsocial.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;

import java.util.ArrayList;
import java.util.List;

@Repository("RepositoryReceveurCustom")
public class RepositoryReceveurCustomImpl implements RepositoryReceveurCustom{


    @Autowired
    private RepositoryReceveur repositoryReceveur;

    @Override
    public Receveur getReceveurByMessage(Message message) {
        List<Receveur> listeReceveur = new ArrayList<>();
        repositoryReceveur.findAll().forEach(listeReceveur::add);
        for(Receveur r : listeReceveur){
            if(r.getReceveur() == message.getReceveur()) {
                return r;
            }
        }

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Receveur newReceveur = new Receveur(
                repositoryReceveur.findAll().size() + 1,
                messages,
                message.getReceveur()
        );

        return newReceveur;
    }

    @Override
    public void save(Receveur receveur) {
        try {
            Receveur receveurExistant = repositoryReceveur.findByIdReceveur(receveur.getIdReception()).get(0);
            List<Message> messages = receveurExistant.getMessages();
            messages.add(receveurExistant.getMessages().size(), receveur.getMessages().get(0));
            receveurExistant.setMessages(messages);

            repositoryReceveur.deleteByIdReceveur(receveur.getIdReception());
            System.out.println(receveurExistant);
            System.out.println(receveurExistant.getReceveur());
            repositoryReceveur.insert(receveurExistant);
        }
        catch (Exception e){
            repositoryReceveur.insert(receveur);
        }
    }
}
