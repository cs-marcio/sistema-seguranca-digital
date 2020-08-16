package br.com.squadra.ssd.repository;

import br.com.squadra.ssd.model.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SistemaRepository extends JpaRepository<Sistema, Long> {
    Sistema findById(long id);
}
