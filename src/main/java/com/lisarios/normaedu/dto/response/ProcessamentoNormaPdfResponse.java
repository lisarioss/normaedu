package com.lisarios.normaedu.dto.response;

import java.util.List;

public record ProcessamentoNormaPdfResponse(
        Long normaId,
        String nomeArquivo,
        String textoExtraido,
        List<ReferenciaNormativaAnalisadaResponse> referenciasAnalisadas
) {
}