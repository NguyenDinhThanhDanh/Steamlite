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

    Social getSocialById(Integer id) throws UnknownEnvoyeurException;

    Collection<Message> getSocialByIdWithId(Integer id, Integer id2) throws UnknownEnvoyeurException;

    Collection<Social> getByIdEnvoyeur(Integer id);
}
