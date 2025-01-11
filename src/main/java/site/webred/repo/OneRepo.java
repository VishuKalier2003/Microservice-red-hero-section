package site.webred.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import site.webred.model.OneHero;

public interface OneRepo extends MongoRepository<OneHero, String> {

}
