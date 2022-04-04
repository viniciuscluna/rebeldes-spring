package luna.vinicius.rebeldesapi.controller;


import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.dto.InventarioRebeldeDto;
import luna.vinicius.rebeldesapi.dto.NegociacaoDto;
import luna.vinicius.rebeldesapi.model.Localizacao;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.service.RebeldeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("rebeldes")
@RequiredArgsConstructor
public class RebeldeController {

    private final RebeldeService rebeldeService;

    @GetMapping
    public ResponseEntity<Page<Rebelde>> get(
            @RequestParam Optional<String> sortBy,
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<Sort.Direction> direction
            ){
        return ResponseEntity.ok(rebeldeService.listarFiltrado(sortBy, page, size, direction));
    }

    @PostMapping
    public ResponseEntity<Rebelde> inserir(@Valid @RequestBody Rebelde rebelde){
        var criado = rebeldeService.inserir(rebelde);
        return ResponseEntity.ok(criado);
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@Valid @RequestBody Rebelde rebelde){
        rebeldeService.atualizar(rebelde);
        return ResponseEntity.ok("Atualizado!");
    }

    @DeleteMapping("/{rebeldeId}")
    public ResponseEntity<String> remover(@PathVariable Integer rebeldeId){
        rebeldeService.remover(rebeldeId);
        return ResponseEntity.ok("Removido!");
    }

    @GetMapping("/{rebeldeId}/inventario")
    public ResponseEntity<InventarioRebeldeDto> inventario(@PathVariable Integer rebeldeId){
        return ResponseEntity.ok(rebeldeService.inventario(rebeldeId));
    }

    @PostMapping("/{rebeldeId}/reportarTraidor")
    public ResponseEntity<String> reportarTraidor(@PathVariable Integer rebeldeId){
        try {
            var resultado = rebeldeService.reportarTraidor(rebeldeId);
            return ResponseEntity.ok(resultado);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{rebeldeId}/atualizaLocalizacao")
    public ResponseEntity<String> atualizaLocalizacao(
            @PathVariable Integer rebeldeId,
            @Valid @RequestBody Localizacao localizacao){
        try{
        var atualizado = rebeldeService.atualizarLocalizacao(localizacao, rebeldeId);
        return ResponseEntity.ok(atualizado);
    }
        catch (Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    }

    @PostMapping("/negociar")
    public ResponseEntity<String> negociar(
            @Valid @RequestBody NegociacaoDto negociacaoDto
            ){
        try {
            var resultado = rebeldeService.negociar(negociacaoDto);
            return ResponseEntity.ok(resultado);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
