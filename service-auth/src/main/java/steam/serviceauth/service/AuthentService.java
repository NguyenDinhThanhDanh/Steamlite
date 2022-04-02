package steam.serviceauth.service;

import org.springframework.stereotype.Repository;

import steam.serviceauth.exception.*;
import steam.serviceauth.entities.ClientKC;

import java.sql.SQLException;

@Repository
public interface AuthentService {
    void createUtilisateur(ClientKC client) throws PseudoDejaPrisException;
    String keycloakToken(String username,String password);
    void setUserPassWord(String pseudo) throws SQLException;


}
