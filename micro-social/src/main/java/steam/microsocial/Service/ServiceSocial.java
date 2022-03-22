package steam.microsocial.Service;

import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

public interface ServiceSocial {
    void sendNewMessage(Message message);

    Social getSocial(Integer idMessage);
}
