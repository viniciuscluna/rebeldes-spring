package luna.vinicius.rebeldesapi.service;

import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.model.ItemInventario;
import luna.vinicius.rebeldesapi.model.Localizacao;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.repository.ItemInventarioRepository;
import luna.vinicius.rebeldesapi.repository.RebeldeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebeldeService {

    private RebeldeRepository repository;
    private ItemInventarioRepository itemInventarioRepository;

    @Autowired
    public RebeldeService(RebeldeRepository repository, ItemInventarioRepository itemInventarioRepository) {

        this.repository = repository;
        this.itemInventarioRepository = itemInventarioRepository;
    }

    public List<Rebelde> listar(){
        return Streamable.of(repository.findAll()).toList();
    }

    public Rebelde inserir(Rebelde rebelde){
        rebelde.setTraidor(false);
        var itens = rebelde.getItens();
        rebelde.setItens(new ArrayList<>());
        var created = repository.save(rebelde);

        for(var item : itens){
            item.setRebelde(created);
        }

        itemInventarioRepository.saveAll(itens);

        created.setItens(itens);
        return created;
    }

    public Localizacao atualizarLocalizacao(Localizacao localizacao, Integer rebeldeId){
        var rebelde = repository.findById(rebeldeId).get();
        rebelde.setLocalizacao(localizacao);
        return repository.save(rebelde).getLocalizacao();
    }

}
