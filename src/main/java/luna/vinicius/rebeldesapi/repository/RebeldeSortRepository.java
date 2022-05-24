package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.Rebelde;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebeldeSortRepository extends ReactiveSortingRepository<Rebelde, Integer> {

}
