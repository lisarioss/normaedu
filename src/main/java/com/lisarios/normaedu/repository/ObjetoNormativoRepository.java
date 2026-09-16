package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.ObjetoNormativo;
import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjetoNormativoRepository
        extends JpaRepository<ObjetoNormativo, Long> {

    List<ObjetoNormativo> findByAtivoTrueOrderByNomeAsc();

    List<ObjetoNormativo> findByTipoAndAtivoTrue(
            TipoObjetoNormativo tipo
    );
}