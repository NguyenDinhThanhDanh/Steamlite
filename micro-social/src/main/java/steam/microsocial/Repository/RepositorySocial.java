package steam.microsocial.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import steam.microsocial.Entities.Social;

import java.util.Collection;
import java.util.List;

public interface RepositorySocial extends MongoRepository<Social, Integer> {


}
