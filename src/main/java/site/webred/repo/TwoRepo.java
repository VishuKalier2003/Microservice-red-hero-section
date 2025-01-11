package site.webred.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import site.webred.model.TwoHero;

@Repository
public interface TwoRepo extends MongoRepository<TwoHero, String> {

}
