package luna.vinicius.rebeldesapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String nome;
    private Integer pontos;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="rebelde_id")
    private Rebelde rebelde;
}
