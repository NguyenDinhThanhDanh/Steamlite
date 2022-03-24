package steam.microsocial.Repository;

import org.springframework.stereotype.Repository;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

@Repository("RepositorySocialCustom")
public interface RepositorySocialCustom {
    void save(Social social);
    Social getSocialByJoueur(Message message);
}
