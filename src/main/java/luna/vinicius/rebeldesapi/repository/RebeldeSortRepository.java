package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebeldeSortRepository extends JpaRepository<Rebelde, Integer> {

}
