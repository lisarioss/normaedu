package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.domain.enums.TipoNorma;

import java.util.List;

public record HistoricoNormaResponse(
        Long normaId,
        TipoNorma tipo,
        String numero,
        Integer ano,
        StatusNorma status,
        String ementa,
        List<HistoricoRelacaoResponse> relacoes
) {
}