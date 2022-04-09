package steam.microsocial.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import steam.microcatalogue.Exception.UnknownEnvoyeurException;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Repository("RepositorySocialCustom")
public class RepositorySocialCustomImpl implements RepositorySocialCustom {

    @Autowired
    private RepositorySocial repositorySocial;


    @Override
    public Social getSocialByJoueur(Message message) {
        List<Social> listeSocial = new ArrayList<>();
        repositorySocial.findAll().forEach(listeSocial::add);
        for(Social s : listeSocial){
            if(s.getEnvoyeur() == message.getEnvoyeur()) {
                return s;
            }
        }

        List<Message> messages = new ArrayList<>();
        messages.add(message);

        Social newSocial = new Social(
                repositorySocial.findAll().size() + 1,
                messages,
                message.getEnvoyeur()
                );

        return newSocial;
    }

    @Override
    public Social getSocialByIdEnvoyeur(Integer idEnvoyeur) throws UnknownEnvoyeurException {
        List<Social> listeSocial = new ArrayList<>();
        repositorySocial.findAll().forEach(listeSocial::add);
        for (Social s : listeSocial) {
            System.out.println("COMPARAISON");
            System.out.println(s.getEnvoyeur());
            System.out.println(idEnvoyeur);
            if (idEnvoyeur == s.getEnvoyeur()) {
                return s;
            }
    }
        throw new UnknownEnvoyeurException();
    }

    //Delete message dans les données de l'envoyeur
    @Override
    public void deleteMessageFromSocial(Social social, Integer idMessage) throws UnknownIdMessageException {
        List<Message> messages = social.getListeMessage();
        try {

            List<Message> newListMessage = messages.stream()
                    .filter(message -> message.getIdMessage() != (idMessage)).collect(Collectors.toList());

            social.setListeMessage(newListMessage);
            repositorySocial.deleteByIdSocial(social.getIdSocial());
            repositorySocial.insert(social);

        }
        catch (Exception e){
            e.printStackTrace();
            throw new UnknownIdMessageException();
        }

    }

    @Override
    public void save(Social social) {
        try {
            Social socialExistant = repositorySocial.findByIdSocial(social.getIdSocial()).get(0);
            List<Message> messages = socialExistant.getListeMessage();
            social.getListeMessage().get(0).setIdMessage(socialExistant.getListeMessage().size());
            messages.add(social.getListeMessage().get(0));
            socialExistant.setListeMessage(messages);

            repositorySocial.deleteByIdSocial(social.getIdSocial());
            repositorySocial.insert(socialExistant);
        }
        catch (Exception e){
            repositorySocial.insert(social);
        }
    }
}
