package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.Rebelde;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RebeldeRepository extends ReactiveCrudRepository<Rebelde, Integer> {

    @Query("select r from Rebelde r where r.login=:login")
    public Flux<Rebelde> filterByLogin(String login);
}
