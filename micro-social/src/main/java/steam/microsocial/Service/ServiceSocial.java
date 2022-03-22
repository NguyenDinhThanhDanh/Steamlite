package steam.microsocial.Service;

import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.util.Collection;

public interface ServiceSocial {
    void sendNewMessage(Message message);

    Collection<Social> getSocialAll();
}
