package steam.microsocial.Service;

import steam.microsocial.Entities.Social;

public interface ServiceSocial {
    Social findMessageById(Integer idJoueur);
    void sendNewMessage(Social social);

}
