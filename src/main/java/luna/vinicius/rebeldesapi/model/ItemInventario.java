package luna.vinicius.rebeldesapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ItemInventario {
    public ItemInventario(Integer id, String nome, Integer pontos){
    this.id = id;
    this.pontos = pontos;
    this.nome = nome;
}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Integer pontos;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="rebelde_id")
    private Rebelde rebelde;
}
