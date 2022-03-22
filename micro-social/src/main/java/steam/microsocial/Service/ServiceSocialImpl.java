package steam.microsocial.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositorySocial;
import steam.microsocial.Repository.SocialRepositoryCustom;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ServiceSocialImpl implements ServiceSocial {

    @Autowired
    private RepositorySocial repositorySocial;

    @Autowired
    private SocialRepositoryCustom socialRepositoryCustom;

    @Override
    public void sendNewMessage(Message message) {
        Social monSocial = socialRepositoryCustom.getSocialByJoueur(message);
        System.out.println("POURQUOI");
        socialRepositoryCustom.save(monSocial);
    }

    @Override
    public Collection<Social> getSocialAll() {

        Collection<Social> social = new ArrayList<>();
        repositorySocial.findAll().forEach(social::add);
        return social;
    }
}
