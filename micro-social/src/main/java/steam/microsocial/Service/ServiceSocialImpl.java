package steam.microsocial.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;
import steam.microsocial.Repository.RepositoryReceveur;
import steam.microsocial.Repository.RepositoryReceveurCustom;
import steam.microsocial.Repository.RepositorySocial;
import steam.microsocial.Repository.RepositorySocialCustom;

import java.util.ArrayList;
import java.util.Collection;

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
}
