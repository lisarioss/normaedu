package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoNorma;
import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;

public record ReferenciaNormativaAnalisadaResponse(
        TipoRelacaoNorma tipoRelacao,
        TipoNorma tipoNorma,
        String numero,
        Integer ano,
        String evidenciaTextual,
        boolean normaEncontrada,
        Long normaId
) {
}