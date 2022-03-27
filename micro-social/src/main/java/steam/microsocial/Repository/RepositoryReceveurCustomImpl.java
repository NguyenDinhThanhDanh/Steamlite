package steam.microsocial.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Receveur getReceveurByIdReceveur(Integer idReceveur) {
        return repositoryReceveur.findByIdReceveur(idReceveur);
    }

    @Override
    public void deleteMessageFromReceveur(Receveur receveur, Integer idMessage) throws UnknownIdMessageException {
        Collection<Message> messages = receveur.getMessages();
        for (Message message : messages){
            if(message.getIdMessage() == idMessage){
                repositoryReceveur.deleteByIdMessage(idMessage);
            }
        }
        throw new UnknownIdMessageException();
    }

    @Override
    public Message getMessageByIdMessage(Social social, Integer idMessage) throws UnknownIdMessageException {
        for (Message m : social.getListeMessage()) {
            System.out.println("idMessage : " + idMessage + " getIdmessage : " + m.getIdMessage());
            if (m.getIdMessage() == idMessage) {
                return  m;
            }
        }
        throw new UnknownIdMessageException();
    }

    @Override
    public void save(Receveur receveur) {
        try {
            Receveur receveurExistant = repositoryReceveur.findByIdReceveur(receveur.getIdReception());
            List<Message> messages = receveurExistant.getMessages();
            receveur.getMessages().get(0).setIdMessage(receveurExistant.getMessages().size());
            messages.add(receveur.getMessages().get(0));
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
