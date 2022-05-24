package luna.vinicius.rebeldesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.*;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Rebelde {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String nome;
    private  Integer idade;
    private String genero;
    private Boolean traidor;
    private String senha;
    private String login;

    @JsonIgnore
    public String getLogin() {
        return login;
    }

    @JsonProperty
    public void setLogin(final String login) {
        this.login = login;
    }



    @JsonIgnore
    public String getSenha() {
        return senha;
    }

    @JsonProperty
    public void setSenha(final String senha) {
        this.senha = senha;
    }

    @Column(value = "role")
    private String role;

    @JsonIgnore
    private Integer reportadoTraidor;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="localizacao_id")
    private Localizacao localizacao;

}
