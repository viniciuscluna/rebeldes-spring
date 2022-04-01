package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.ItemInventario;
import luna.vinicius.rebeldesapi.model.Rebelde;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RebeldeRepository extends CrudRepository<Rebelde, Integer> {

    @Query("select r from Rebelde r where r.login=:login")
    public Iterable<Rebelde> filterByLogin(@Param("login") String login);
}
