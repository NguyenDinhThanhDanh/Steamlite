package steam.microsocial.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import steam.microsocial.Entities.Social;

import java.util.List;

public interface RepositorySocial extends MongoRepository<Social, Integer> {
    @Query(value = "{'idSocial': ?0}")
    List<Social> findByIdSocial(int id);

    @Query(value = "{'idSocial': ?0}", delete = true)
    void deleteByIdSocial(int id);

    @Query(value = "{'messages.idMessage': ?0}", delete = true)
    void deleteByIdMessage(int id);
}
