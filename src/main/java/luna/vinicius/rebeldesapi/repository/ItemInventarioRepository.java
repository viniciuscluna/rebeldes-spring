package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.ItemInventario;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioRepository extends ReactiveCrudRepository<ItemInventario, Integer> {

    @Query("select iv from ItemInventario iv join iv.rebelde r where r.id=:rebeldeId")
    public Iterable<ItemInventario> filterByRebelde(@Param("rebeldeId") Integer rebeldeId);

}