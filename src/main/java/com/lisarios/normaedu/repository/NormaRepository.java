package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.domain.enums.TipoNorma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NormaRepository extends JpaRepository<Norma, Long> {

    List<Norma> findByOrgaoId(Long orgaoId);

    List<Norma> findByAssuntosId(Long assuntoId);

    List<Norma> findByObjetosId(Long objetoId);

    List<Norma> findByEmentaContainingIgnoreCase(String termo);

    List<Norma> findByNumero(String numero);

    List<Norma> findByAno(Integer ano);

    List<Norma> findByTipo(TipoNorma tipo);

    List<Norma> findByStatus(StatusNorma status);

    List<Norma> findByOrgaoIdAndStatus(
            Long orgaoId,
            StatusNorma status
    );

    List<Norma> findByTipoAndAno(
            TipoNorma tipo,
            Integer ano
    );

    @Query("""
        SELECT n
        FROM Norma n
        WHERE (:termo IS NULL
                OR LOWER(n.ementa) LIKE LOWER(CONCAT(CONCAT('%', :termo), '%')))
        AND (:ano IS NULL OR n.ano = :ano)
        AND (:tipo IS NULL OR n.tipo = :tipo)
        AND (:status IS NULL OR n.status = :status)
        AND (:orgaoId IS NULL OR n.orgao.id = :orgaoId)
        ORDER BY n.ano DESC, n.numero ASC
        """)
        List<Norma> buscarComFiltros(
                @Param("termo") String termo,
                @Param("ano") Integer ano,
                @Param("tipo") TipoNorma tipo,
                @Param("status") StatusNorma status,
                @Param("orgaoId") Long orgaoId
        );

}