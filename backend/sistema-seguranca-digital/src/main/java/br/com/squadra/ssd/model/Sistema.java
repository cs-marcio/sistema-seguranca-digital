package br.com.squadra.ssd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode
public class Sistema {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String descricao;
    private String sigla;
    private String email;
    private String url;
}
