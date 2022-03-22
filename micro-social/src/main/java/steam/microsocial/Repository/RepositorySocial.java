package steam.microsocial.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import steam.microsocial.Entities.Social;

public interface RepositorySocial extends MongoRepository<Social, Integer> {

}
