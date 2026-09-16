package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.domain.enums.TipoNorma;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record NormaResponse(

        Long id,
        TipoNorma tipo,
        String numero,
        Integer ano,
        String ementa,
        LocalDate dataPublicacao,
        LocalDate dataVigencia,
        StatusNorma status,
        String textoIntegral,
        String fonte,
        String urlFonte,

        Long orgaoId,
        String orgaoNome,

        Set<AssuntoResumoResponse> assuntos,
        Set<ObjetoNormativoResumoResponse> objetos,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}