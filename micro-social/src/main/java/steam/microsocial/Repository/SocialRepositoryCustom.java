package steam.microsocial.Repository;

import org.springframework.stereotype.Repository;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

@Repository("SocialRepositoryCustom")
public interface SocialRepositoryCustom {
    void save(Social social);
    Social getSocialByJoueur(Message message);
}
