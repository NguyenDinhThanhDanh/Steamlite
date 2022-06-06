package steam.microsocial.Repository;

import org.springframework.stereotype.Repository;
import steam.microcatalogue.Exception.UnknownEnvoyeurException;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.util.Collection;

@Repository("RepositorySocialCustom")
public interface RepositorySocialCustom {
    void save(Social social);
    Social getSocialByJoueur(Message message);
    Social getSocialByIdEnvoyeur(Integer idEnvoyeur) throws UnknownEnvoyeurException;

    void deleteMessageFromSocial(Social social, Integer idMessage) throws UnknownIdMessageException;

}
