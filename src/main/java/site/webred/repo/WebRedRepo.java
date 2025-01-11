package site.webred.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import site.webred.model.WebRed;

@Repository
public interface WebRedRepo extends MongoRepository<WebRed, String> {

}
