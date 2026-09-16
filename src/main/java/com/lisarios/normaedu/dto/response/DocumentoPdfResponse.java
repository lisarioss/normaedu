package com.lisarios.normaedu.dto.response;

import java.util.List;

public record DocumentoPdfResponse(
        String nomeArquivo,
        String textoExtraido,
        List<ReferenciaNormativaDetectadaResponse> referenciasDetectadas
) {
}