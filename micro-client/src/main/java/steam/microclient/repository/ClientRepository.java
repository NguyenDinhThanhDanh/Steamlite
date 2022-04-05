package steam.microclient.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import steam.microclient.entities.Client;


@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    @Query("select c from Client c where c.pseudo=?1")
    Client findClientByPseudo(String pseudo);
    @Query("select c from Client c where c.pseudo =?1 and c.mdp=?2")
    Client findClientByPseudoAndAndMdp(String pseudo, String mdp);
}
