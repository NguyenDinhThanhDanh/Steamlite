package steam.microsocial.Repository;

import org.springframework.stereotype.Repository;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;

@Repository("RepositoryReceveurCustom")
public interface RepositoryReceveurCustom {
    void save(Receveur receveur);
    Receveur getReceveurByMessage(Message message);
}
