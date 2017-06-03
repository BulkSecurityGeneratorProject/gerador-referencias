package br.com.unifacisa.gerador.repository;

import br.com.unifacisa.gerador.domain.Monografia;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Monografia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonografiaRepository extends MongoRepository<Monografia,String> {

}
