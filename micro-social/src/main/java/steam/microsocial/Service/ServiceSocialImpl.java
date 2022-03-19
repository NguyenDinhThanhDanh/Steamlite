package steam.microsocial.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositorySocial;

import javax.persistence.Entity;

@Service
public class ServiceSocialImpl implements ServiceSocial {

    @Autowired
    private RepositorySocial repositorySocial;

    @Override
    public Social findMessageById(Integer idJoueur) {
        return null;
    }

    @Override
    public void sendNewMessage(Social social) {
        repositorySocial.save(social);
    }
}
