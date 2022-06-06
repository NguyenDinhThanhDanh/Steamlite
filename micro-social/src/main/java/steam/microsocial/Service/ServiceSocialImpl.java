package steam.microsocial.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microcatalogue.Exception.UnknownEnvoyeurException;
import steam.microcatalogue.Exception.UnknownIdMessageException;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositoryReceveur;
import steam.microsocial.Repository.RepositoryReceveurCustom;
import steam.microsocial.Repository.RepositorySocial;
import steam.microsocial.Repository.RepositorySocialCustom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceSocialImpl implements ServiceSocial {

    @Autowired
    private RepositorySocial repositorySocial;

    @Autowired
    private RepositoryReceveur repositoryReceveur;

    @Autowired
    private RepositorySocialCustom repositorySocialCustom;

    @Autowired
    private RepositoryReceveurCustom repositoryReceveurCustom;

    @Override
    public void sendNewMessage(Message message) {

        //vérification de l'existence du social
        Social monSocial = repositorySocialCustom.getSocialByJoueur(message);
        // création du social en fonction de son existence
        repositorySocialCustom.save(monSocial);

        //Vérification de l'existence du receveur en base
        Receveur receveur = repositoryReceveurCustom.getReceveurByMessage(message);
        //création du receveur en fonction de son existence
        repositoryReceveurCustom.save(receveur);
    }

    @Override
    public Collection<Social> getSocialAll() {

        Collection<Social> social = new ArrayList<>();
        repositorySocial.findAll().forEach(social::add);
        return social;
    }

    @Override
    public Collection<Receveur> getRecveurAll() {
        Collection<Receveur> receveurs = new ArrayList<>();
        repositoryReceveur.findAll().forEach(receveurs::add);
        return receveurs;
    }

    @Override
    public Message getMessageById(Integer idMessage) {
        return null;
    }

    @Override
    public void deleteMessage(Integer idEnvoyeur,Integer idMessage) throws UnknownEnvoyeurException {
        Social social = null;
        Receveur receveur = null;
        try{
            social =  repositorySocialCustom.getSocialByIdEnvoyeur(idEnvoyeur);
            Message message = repositoryReceveurCustom.getMessageByIdMessage(social, idMessage);
            receveur = repositoryReceveurCustom.getReceveurByIdReceveur(message.getIdMessage());
            repositorySocialCustom.deleteMessageFromSocial(social, idMessage);
            repositoryReceveurCustom.deleteMessageFromReceveur(receveur, idMessage);
        }
        catch (UnknownIdMessageException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteSocial(Integer idSocial) {
        repositorySocial.deleteByIdSocial(idSocial);
    }

    @Override
    public Social getSocialById(Integer id) throws UnknownEnvoyeurException {
        try {
            return repositorySocialCustom.getSocialByIdEnvoyeur(id);
        } catch (UnknownEnvoyeurException e) {
            throw new UnknownEnvoyeurException();
        }
    }

    @Override
    public Collection<Message> getSocialByIdWithId(Integer id, Integer id2) throws UnknownEnvoyeurException {
        Collection<Social> social = repositorySocial.getSocialByIdWithId(id, id2);
        Collection<Message> messages = new ArrayList<>();
        messages = social.stream().toList().get(0).getListeMessage();
        System.out.println(messages);
        if (messages.isEmpty()){
            throw new UnknownEnvoyeurException();
        }
        return messages;
    }

    @Override
    public Collection<Social> getByIdEnvoyeur(Integer id) {
        return repositorySocial.getByIdEnvoyeur(id);
    }
}
