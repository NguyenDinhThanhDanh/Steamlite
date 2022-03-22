package steam.microsocial.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositorySocial;
import steam.microsocial.Repository.SocialRepositoryCustom;

import javax.persistence.Entity;

@Service
public class ServiceSocialImpl implements ServiceSocial {

    @Autowired
    private RepositorySocial repositorySocial;

    @Autowired
    private SocialRepositoryCustom socialRepositoryCustom;

    @Override
    public void sendNewMessage(Message message) {
        Social monSocial = socialRepositoryCustom.getSocialByJoueur(message);
        System.out.println("iciiiiiiiiiiii");
        socialRepositoryCustom.save(monSocial);
    }

    @Override
    public Social getSocial(Integer idMessage) {
        return repositorySocial.findById(idMessage).get();
    }
}
