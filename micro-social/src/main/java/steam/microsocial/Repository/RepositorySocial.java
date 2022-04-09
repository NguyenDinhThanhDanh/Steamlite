package steam.microsocial.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import steam.microsocial.Entities.Message;
import steam.microsocial.Entities.Social;

import java.util.Collection;
import java.util.List;

public interface RepositorySocial extends MongoRepository<Social, Integer> {
    @Query(value = "{'idSocial': ?0}")
    List<Social> findByIdSocial(int id);

    List<Social> findSocialsBy();

    @Query(value = "{'idSocial': ?0}", delete = true)
    void deleteByIdSocial(int id);

    @Query(value = "{'envoyeur' : ?0, 'messages.receveur' : ?1}")
    Collection<Social> getSocialByIdWithId(Integer id, Integer id2);

    @Query(value = "{'envoyeur' : ?0}")
    Collection<Social> getByIdEnvoyeur(Integer id);
}
