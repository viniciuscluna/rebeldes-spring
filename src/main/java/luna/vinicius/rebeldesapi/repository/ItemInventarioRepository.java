package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.ItemInventario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioRepository extends CrudRepository<ItemInventario, Integer> {
}