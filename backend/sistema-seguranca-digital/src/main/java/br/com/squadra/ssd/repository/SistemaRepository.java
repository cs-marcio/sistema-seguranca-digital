package br.com.squadra.ssd.repository;

import br.com.squadra.ssd.model.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface SistemaRepository extends JpaRepository<Sistema, Long>, QueryByExampleExecutor<Sistema> {
    Sistema findById(long id);
}
