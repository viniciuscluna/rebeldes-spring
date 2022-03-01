package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.Rebelde;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebeldeRepository extends CrudRepository<Rebelde, Integer> {
}
