package luna.vinicius.rebeldesapi.service;

import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.dto.InventarioRebeldeDto;
import luna.vinicius.rebeldesapi.dto.NegociacaoDto;
import luna.vinicius.rebeldesapi.model.Localizacao;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.repository.ItemInventarioRepository;
import luna.vinicius.rebeldesapi.repository.RebeldeRepository;
import luna.vinicius.rebeldesapi.repository.RebeldeSortRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebeldeService {

    private final RebeldeRepository repository;
    private final RebeldeSortRepository repositorySort;
    private final ItemInventarioRepository itemInventarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<Rebelde> listarFiltrado(Optional<String> sortBy, Optional<Integer> page, Optional<Integer> size, Optional<Sort.Direction> direction) {
        return repositorySort.findAll(PageRequest.of(page.orElse(0), size.orElse(10), direction.orElse(Sort.Direction.ASC), sortBy.orElse("id")
        ));
    }

    public void remover(Integer rebeldeId) {
        var rebelde = repository.findById(rebeldeId).get();

        repository.delete(rebelde);
    }

    public void atualizar(Rebelde rebelde){
        repository.save(rebelde);
    }

    public InventarioRebeldeDto inventario(int rebeldeId){
        var rebelde = repository.findById(rebeldeId).get();
        var inventario =  IterableUtils.toList(itemInventarioRepository.filterByRebelde(rebeldeId));
        return new InventarioRebeldeDto(rebelde.getNome(), inventario);
    }

    public Rebelde inserir(Rebelde rebelde) {
        rebelde.setTraidor(false);
        rebelde.setReportadoTraidor(0);
        rebelde.setRole("ROLE_REBELDE");
        rebelde.setSenha(passwordEncoder.encode(rebelde.getSenha()));
        var itens = rebelde.getItens();
        rebelde.setItens(new ArrayList<>());
        var created = repository.save(rebelde);

        for (var item : itens) {
            item.setRebelde(created);
        }

        itemInventarioRepository.saveAll(itens);

        created.setItens(itens);
        return created;
    }

    public Rebelde inserirAdmin(Rebelde rebelde) {
        rebelde.setTraidor(false);
        rebelde.setReportadoTraidor(0);
        rebelde.setRole("ROLE_ADMIN");
        rebelde.setSenha(passwordEncoder.encode(rebelde.getSenha()));

        return repository.save(rebelde);
    }


    public String reportarTraidor(Integer rebeldeId) throws Exception {
        var consultaRebelde = repository.findById(rebeldeId);
        if (consultaRebelde.isPresent()) {
            String resultado = "Reportado";
            var rebelde = consultaRebelde.get();
            int vezesReportado = rebelde.getReportadoTraidor() + 1;

            rebelde.setReportadoTraidor(vezesReportado);

            if (vezesReportado == 3) {
                rebelde.setTraidor(true);
                rebelde.setRole("ROLE_TRAIDOR");
                resultado = "Reportado e virou traidor";
            }
            repository.save(rebelde);
            return resultado;
        } else throw new Exception("Rebelde não encontrado");
    }

    public String atualizarLocalizacao(Localizacao localizacao, Integer rebeldeId) {
        var rebelde = repository.findById(rebeldeId).get();
        localizacao.setAtualizada(true);
        rebelde.setLocalizacao(localizacao);
        repository.save(rebelde);
        return "Localização atualizada";
    }

    public String negociar(NegociacaoDto negociacaoDto) throws Exception {
        var rebeldeA = repository.findById(negociacaoDto.getRebeldeAId()).get();
        var itensRebeldeA = IterableUtils.toList(itemInventarioRepository.filterByRebelde(negociacaoDto.getRebeldeAId()));
        var rebeldeB = repository.findById(negociacaoDto.getRebeldeBId()).get();
        var itensRebeldeB = IterableUtils.toList(itemInventarioRepository.filterByRebelde(negociacaoDto.getRebeldeBId()));

        if (itensRebeldeA.stream().mapToInt(m -> m.getPontos()).sum() == itensRebeldeB.stream().mapToInt(m -> m.getPontos()).sum()) {

            for (var itemA : itensRebeldeA) {
                itemA.setRebelde(rebeldeB);
            }

            for (var itemB : itensRebeldeB) {
                itemB.setRebelde(rebeldeA);
            }

            itemInventarioRepository.saveAll(itensRebeldeA);
            itemInventarioRepository.saveAll(itensRebeldeB);

            return "Negociação finalizada";
        } else throw new Exception("Quantidade entre pontos entre os rebeldes é diferente... abortando.");

    }
}
