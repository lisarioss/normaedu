package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.domain.enums.TipoNorma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NormaRepository extends JpaRepository<Norma, Long> {

    List<Norma> findByOrgaoId(Long orgaoId);

    List<Norma> findByAssuntosId(Long assuntoId);

    List<Norma> findByObjetosId(Long objetoId);

    List<Norma> findByOrgaoIdAndStatus(
            Long orgaoId,
            StatusNorma status
    );

    List<Norma> findByTipoAndAno(
            TipoNorma tipo,
            Integer ano
    );
}