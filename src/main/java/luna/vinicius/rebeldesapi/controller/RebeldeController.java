package luna.vinicius.rebeldesapi.controller;


import lombok.RequiredArgsConstructor;
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

    @PutMapping("/atualizaLocalizacao/{rebeldeId}")
    public ResponseEntity<Localizacao> atualizaLocalizacao(
            @PathVariable Integer rebeldeId,
            @Valid @RequestBody Localizacao localizacao){
        var atualizado = rebeldeService.atualizarLocalizacao(localizacao, rebeldeId);
        return ResponseEntity.ok(atualizado);
    }

}
