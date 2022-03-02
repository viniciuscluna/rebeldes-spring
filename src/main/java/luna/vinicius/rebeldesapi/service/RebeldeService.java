package luna.vinicius.rebeldesapi.service;

import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.dto.NegociacaoDto;
import luna.vinicius.rebeldesapi.model.ItemInventario;
import luna.vinicius.rebeldesapi.model.Localizacao;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.repository.ItemInventarioRepository;
import luna.vinicius.rebeldesapi.repository.RebeldeRepository;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
        return IterableUtils.toList(repository.findAll());
    }

    public Rebelde inserir(Rebelde rebelde){
        rebelde.setTraidor(false);
        rebelde.setReportadoTraidor(0);
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

    public String reportarTraidor(Integer rebeldeId) throws Exception {
        var consultaRebelde = repository.findById(rebeldeId);
        if(consultaRebelde.isPresent()) {
            String resultado = "Reportado";
            var rebelde = consultaRebelde.get();
            Integer vezesReportado = rebelde.getReportadoTraidor() + 1;

            rebelde.setReportadoTraidor(vezesReportado);

            if (vezesReportado == 3) {
                rebelde.setTraidor(true);
                resultado = "Reportado e virou traidor";
            }
            repository.save(rebelde);
            return resultado;
        }
        else throw new Exception("Rebelde não encontrado");
    }

    public String atualizarLocalizacao(Localizacao localizacao, Integer rebeldeId){
        var rebelde = repository.findById(rebeldeId).get();
        rebelde.setLocalizacao(localizacao);
        return "Localização atualizada: " + repository.save(rebelde).getLocalizacao().toString();
    }

    public String negociar(NegociacaoDto negociacaoDto) throws Exception {
        var rebeldeA = repository.findById(negociacaoDto.getRebeldeAId()).get();
        var itensRebeldeA = IterableUtils.toList(itemInventarioRepository.filterByRebelde(negociacaoDto.getRebeldeAId()));
        var rebeldeB = repository.findById(negociacaoDto.getRebeldeBId()).get();
        var itensRebeldeB = IterableUtils.toList(itemInventarioRepository.filterByRebelde(negociacaoDto.getRebeldeBId()));

        if(itensRebeldeA.stream().mapToInt(m-> m.getPontos()).sum() == itensRebeldeB.stream().mapToInt(m-> m.getPontos()).sum()){

            for(var itemA : itensRebeldeA){
                itemA.setRebelde(rebeldeB);
            }

            for(var itemB : itensRebeldeB){
                itemB.setRebelde(rebeldeA);
            }

            itemInventarioRepository.saveAll(itensRebeldeA);
            itemInventarioRepository.saveAll(itensRebeldeB);

            return "Negociação finalizada";
        }
        else throw new Exception("Quantidade entre pontos entre os rebeldes é diferente... abortando.");

    }
}
