package luna.vinicius.rebeldesapi.controller;


import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.dto.NegociacaoDto;
import luna.vinicius.rebeldesapi.model.Localizacao;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.service.RebeldeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("rebeldes")
@RequiredArgsConstructor
public class RebeldeController {

    private final RebeldeService rebeldeService;

    @GetMapping
    public ResponseEntity<List<Rebelde>> buscar(){
        var lista = rebeldeService.listar();
        return  ResponseEntity.ok(lista);
    }

    @PostMapping("/inserir")
    public ResponseEntity<Rebelde> inserir(@Valid @RequestBody Rebelde rebelde){
        var criado = rebeldeService.inserir(rebelde);
        return ResponseEntity.ok(criado);
    }

    @PostMapping("/reportarTraidor/{rebeldeId}")
    public ResponseEntity<String> reportarTraidor(@PathVariable Integer rebeldeId){
        try {
            var resultado = rebeldeService.reportarTraidor(rebeldeId);
            return ResponseEntity.ok(resultado);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/atualizaLocalizacao/{rebeldeId}")
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
