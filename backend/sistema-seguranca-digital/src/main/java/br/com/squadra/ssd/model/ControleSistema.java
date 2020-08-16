package br.com.squadra.ssd.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @EqualsAndHashCode
public class ControleSistema {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_sistema")
    private Sistema id_sistema;

    private String status;
    private String usuario;
    private LocalDate dataAlteracao;
    private String justificativa;
}
