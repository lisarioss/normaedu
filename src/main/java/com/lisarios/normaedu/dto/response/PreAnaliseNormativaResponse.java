package com.lisarios.normaedu.dto.response;

import java.util.List;

public record PreAnaliseNormativaResponse(
        Long objetoId,
        String objetoNome,
        Integer quantidadeNormas,
        Integer quantidadeRelacoesConhecidas,
        String resultado,
        String observacao,
        List<RelacaoNormaResponse> relacoesConhecidas
) {
}