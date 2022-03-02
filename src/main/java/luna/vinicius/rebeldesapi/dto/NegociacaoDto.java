package luna.vinicius.rebeldesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NegociacaoDto {
    private Integer[] itensrebeldeA;
    private int rebeldeAId;
    private Integer[] itensrebeldeB;
    private int rebeldeBId;
}
