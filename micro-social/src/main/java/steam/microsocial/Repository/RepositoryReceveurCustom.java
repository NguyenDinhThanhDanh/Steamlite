package steam.microsocial.Repository;

import org.springframework.stereotype.Repository;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;

import java.util.stream.Stream;

@Repository("RepositoryReceveurCustom")
public interface RepositoryReceveurCustom {
    void save(Receveur receveur);
    Receveur getReceveurByMessage(Message message);

    Receveur getReceveurByIdReceveur(Integer idReceveur);

    void deleteMessageFromReceveur(Receveur receveur, Integer idMessage) throws UnknownIdMessageException;

    Message getMessageByIdMessage(Social social, Integer idMessage) throws UnknownIdMessageException;
}
