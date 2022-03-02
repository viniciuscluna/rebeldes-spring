package luna.vinicius.rebeldesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RelatorioTipoRecursoDto {
    private String nomeRecurso;
    private Long quantidade;
}
