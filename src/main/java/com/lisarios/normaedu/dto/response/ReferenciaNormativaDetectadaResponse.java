package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoNorma;
import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;

public record ReferenciaNormativaDetectadaResponse(
        TipoRelacaoNorma tipoRelacao,
        TipoNorma tipoNorma,
        String numero,
        Integer ano,
        String evidenciaTextual
) {
}