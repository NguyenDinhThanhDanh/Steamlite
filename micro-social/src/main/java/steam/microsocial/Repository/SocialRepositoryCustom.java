package steam.microsocial.Repository;

import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

public interface SocialRepositoryCustom {
    void save(Social social);
    Social getSocialByJoueur(Message message);
}
