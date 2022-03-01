package luna.vinicius.rebeldesapi.repository;

import luna.vinicius.rebeldesapi.model.Localizacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoRepository extends CrudRepository<Localizacao, Integer> {
}