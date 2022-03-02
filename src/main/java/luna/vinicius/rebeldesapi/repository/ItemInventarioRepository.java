package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.ItemInventario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioRepository extends CrudRepository<ItemInventario, Integer> {
    @Query("select iv from ItemInventario iv join iv.rebelde r where r.id=:rebeldeId")
    public Iterable<ItemInventario> filterByRebelde(@Param("rebeldeId") Integer rebeldeId);
}