package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.AlertaNormativo;
import com.lisarios.normaedu.domain.enums.StatusAlertaNormativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaNormativoRepository
        extends JpaRepository<AlertaNormativo, Long> {

    List<AlertaNormativo> findByStatus(
            StatusAlertaNormativo status
    );

    List<AlertaNormativo> findByObjetoNormativoId(
            Long objetoNormativoId
    );

    boolean existsByObjetoNormativoIdAndNormaAIdAndNormaBId(
            Long objetoNormativoId,
            Long normaAId,
            Long normaBId
    );
}