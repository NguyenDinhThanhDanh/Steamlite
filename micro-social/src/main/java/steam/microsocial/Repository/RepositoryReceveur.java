package steam.microsocial.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import steam.microsocial.Entities.Receveur;
import steam.microsocial.Entities.Social;

import java.util.List;

public interface RepositoryReceveur extends MongoRepository<Receveur, Integer> {
    @Query(value = "{'idReception': ?0}")
    Receveur findByIdReceveur(int id);

    @Query(value = "{'idReception': ?0}", delete = true)
    void deleteByIdReceveur(int id);

}
