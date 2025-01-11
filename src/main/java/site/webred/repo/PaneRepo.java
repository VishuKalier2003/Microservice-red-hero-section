package site.webred.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import site.webred.model.KeyPane;

@Repository
public interface PaneRepo extends MongoRepository<KeyPane, String> {

}
