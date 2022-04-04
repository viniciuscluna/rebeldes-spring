package luna.vinicius.rebeldesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import luna.vinicius.rebeldesapi.model.ItemInventario;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventarioRebeldeDto {
    private String rebelde;
    private List<ItemInventario> itens;
}
