package steam.microsocial.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SocialRepositoryCustomImpl implements SocialRepositoryCustom{

    @Autowired
    private RepositorySocial repositorySocial;

    @Override
    public Social getSocialByJoueur(Message message) {
        List<Social> listeSocial = new ArrayList<>();
        repositorySocial.findAll().forEach(listeSocial::add);
        for(Social e : listeSocial){
            if(e.getEnvoyeur() == message.getEnvoyeur()) {
                return e;
            }
        }

        int idS;

        List<Message> messages = new ArrayList<>();
        messages.add(message);


        if(listeSocial.size()==0){
            idS = 1;
        }
        else{
            idS = listeSocial.get(listeSocial.size() - 1).getIdSocial()+1;
        }

        Social newSocial = new Social(
                idS,
                messages,
                message.getEnvoyeur()
                );

        return newSocial;
    }

    @Override
    public void save(Social social) {
        try {
            Social socialExistant = repositorySocial.findById(social.getIdSocial()).stream().collect(Collectors.toList()).get(0);
            List<Message> messages = socialExistant.getListeMessage();
            messages.add(socialExistant.getListeMessage().size(), social.getListeMessage().get(0));
            socialExistant.setListeMessage(messages);

            repositorySocial.delete(repositorySocial.findById(socialExistant.getIdSocial()).stream().collect(Collectors.toList()).get(0));
            System.out.println("ici");
            System.out.println(socialExistant);
            System.out.println(socialExistant.getEnvoyeur());
            repositorySocial.insert(socialExistant);
        }
        catch (Exception e){
            System.out.println("iciiiiiiiis");
            List<Message> messages = new ArrayList<>();
            messages.add(social.getListeMessage().get(0));

            Social newSocial = new Social(
                    social.getListeMessage().get(0).getIdMessage(),
                    messages,
                    social.getEnvoyeur()

            );
            System.out.println("ET LAA ?");

            repositorySocial.insert(newSocial);
        }
    }
}
