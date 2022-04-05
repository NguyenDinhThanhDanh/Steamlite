package steam.microsocial.Service;

import steam.microcatalogue.Exception.UnknownEnvoyeurException;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;

import java.util.Collection;

public interface ServiceSocial {
    void sendNewMessage(Message message);

    Collection<Social> getSocialAll();

    Collection<Receveur> getRecveurAll();

    Message getMessageById(Integer idMessage);

    void deleteMessage(Integer idEnvoyeur,Integer idMessage) throws UnknownEnvoyeurException, UnknownIdMessageException;

    void deleteSocial(Integer idSocial);
}
