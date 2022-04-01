package luna.vinicius.rebeldesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Rebelde {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private  Integer idade;
    private String genero;
    private Boolean traidor;
    private String login;

    @JsonIgnore
    private String senha;

    @JsonIgnore
    private String role;

    @JsonIgnore
    private Integer reportadoTraidor;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name="localizacao_id")
    private Localizacao localizacao;

    @JsonManagedReference
    @OneToMany(mappedBy = "rebelde", cascade = CascadeType.PERSIST)
    private Collection<ItemInventario> itens;
}
