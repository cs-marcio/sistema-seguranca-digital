package br.com.squadra.ssd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ComparisonChain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @EqualsAndHashCode
public class Sistema implements Comparable<Sistema> {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String descricao;
    private String sigla;
    private String email;
    private String url;
    private String status;

    @Override
    public int compareTo(Sistema o) {
        ComparisonChain.start()
                .compare(this.descricao, o.descricao)
                .compare(this.sigla, o.sigla)
                .compare(this.email, o.email)
                .compare(this.url, o.url)
                .compare(this.status, o.status)
                .result();
        return 0;
    }
}
