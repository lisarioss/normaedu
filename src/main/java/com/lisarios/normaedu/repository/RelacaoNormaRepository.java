package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.RelacaoNorma;
import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelacaoNormaRepository
        extends JpaRepository<RelacaoNorma, Long> {

    List<RelacaoNorma> findByNormaOrigemId(Long normaOrigemId);

    List<RelacaoNorma> findByNormaDestinoId(Long normaDestinoId);

    List<RelacaoNorma> findByNormaOrigemIdOrNormaDestinoId(
            Long normaOrigemId,
            Long normaDestinoId
    );

    boolean existsByNormaOrigemIdAndNormaDestinoIdAndTipoRelacao(
            Long normaOrigemId,
            Long normaDestinoId,
            TipoRelacaoNorma tipoRelacao
    );
}