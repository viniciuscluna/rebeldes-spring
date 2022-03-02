package luna.vinicius.rebeldesapi.service;

import luna.vinicius.rebeldesapi.dto.RelatorioTipoRecursoDto;
import luna.vinicius.rebeldesapi.repository.ItemInventarioRepository;
import luna.vinicius.rebeldesapi.repository.RebeldeRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RebeldeRelatorioService {
    private RebeldeRepository repository;
    private ItemInventarioRepository itemInventarioRepository;

    @Autowired
    public RebeldeRelatorioService(RebeldeRepository repository, ItemInventarioRepository itemInventarioRepository) {

        this.repository = repository;
        this.itemInventarioRepository = itemInventarioRepository;
    }


    public Long porcentagemTraidores() {
        var total = IterableUtils.toList(repository.findAll());
        var traidores = total.stream().filter(f -> f.getTraidor()).count();
        return traidores * 100 / total.stream().count();
    }

    public Long porcentagemRebeldes() {
        var total = IterableUtils.toList(repository.findAll());
        var rebeldes = total.stream().filter(f -> !f.getTraidor()).count();
        return rebeldes * 100 / total.stream().count();
    }

    public List<RelatorioTipoRecursoDto> relatorioTipoRecurso(){
        var totalRebeldes = IterableUtils.toList(repository.findAll()).stream().filter(f -> !f.getTraidor());
        var group = totalRebeldes.flatMap(m -> m.getItens().stream()).collect(Collectors.groupingBy(g -> g.getNome()));
        return group.entrySet().stream().map(m -> new RelatorioTipoRecursoDto(m.getKey(), m.getValue().stream().count())).toList();
    }

    public Long pontosPerdidos(){
        var totalTraidores = IterableUtils.toList(repository.findAll()).stream().filter(f -> f.getTraidor());
        return totalTraidores.flatMap(m -> m.getItens().stream()).mapToLong(m->m.getPontos()).sum();
    }
}
