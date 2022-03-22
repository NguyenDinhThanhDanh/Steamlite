package steam.microsocial.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository("SocialRepositoryCustom")
public class SocialRepositoryCustomImpl implements SocialRepositoryCustom{

    @Autowired
    private RepositorySocial repositorySocial;

    @Override
    public Social getSocialByJoueur(Message message) {
        List<Social> listeSocial = new ArrayList<>();
        repositorySocial.findAll().forEach(listeSocial::add);
        for(Social e : listeSocial){
            if(e.getEnvoyeur() == message.getEnvoyeur()) {
                System.out.println("CEST GAMEEE");
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

            Social socialExistant = repositorySocial.findById(social.getIdSocial()).get();
            System.out.println(socialExistant);
            List<Message> messages = socialExistant.getListeMessage();
            messages.add(socialExistant.getListeMessage().size(), social.getListeMessage().get(0));
            System.out.println(messages.size());
            System.out.println(messages);
            socialExistant.setListeMessage(messages);

            repositorySocial.delete(social);
            System.out.println(socialExistant);
            System.out.println(socialExistant.getEnvoyeur());
            repositorySocial.insert(socialExistant);
        }
        catch (Exception e){
            System.out.println("DU COUP ICI");
            List<Message> messages = new ArrayList<>();
            messages.add(social.getListeMessage().get(0));


            Social newSocial = new Social(
                    repositorySocial.findAll().size() + 1,
                    messages,
                    social.getEnvoyeur()

            );

            repositorySocial.insert(newSocial);
        }
    }
}
