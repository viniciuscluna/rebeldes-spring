package luna.vinicius.rebeldesapi.controller;

import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.dto.RelatorioTipoRecursoDto;
import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.service.RebeldeRelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rebeldesRelatorio")
@RequiredArgsConstructor
public class RebeldeRelatorioController {

    private final RebeldeRelatorioService rebeldeRelatorioService;


    @GetMapping("/pctRebeldes")
    public ResponseEntity<Long> pctRebeldes(){
        return  ResponseEntity.ok(rebeldeRelatorioService.porcentagemRebeldes());
    }

    @GetMapping("/pctTraidores")
    public ResponseEntity<Long> pctTraidores(){
        return  ResponseEntity.ok(rebeldeRelatorioService.porcentagemTraidores());
    }

    @GetMapping("/tipoRecurso")
    public ResponseEntity<List<RelatorioTipoRecursoDto>> tipoRecurso(){
        return ResponseEntity.ok(rebeldeRelatorioService.relatorioTipoRecurso());
    }

    @GetMapping("/pontosPerdidos")
    public ResponseEntity<Long> pontosPerdidos(){
        return ResponseEntity.ok(rebeldeRelatorioService.pontosPerdidos());
    }

}
