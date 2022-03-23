package steam.microsocial.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.util.ArrayList;
import java.util.List;

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
    public void save(Social social) {
        try {
            Social socialExistant = repositorySocial.findByIdSocial(social.getIdSocial()).get(0);
            List<Message> messages = socialExistant.getListeMessage();
            messages.add(socialExistant.getListeMessage().size(), social.getListeMessage().get(0));
            socialExistant.setListeMessage(messages);

            repositorySocial.deleteByIdSocial(social.getIdSocial());
            System.out.println(socialExistant);
            System.out.println(socialExistant.getEnvoyeur());
            repositorySocial.insert(socialExistant);
        }
        catch (Exception e){
            repositorySocial.insert(social);
        }
    }
}
