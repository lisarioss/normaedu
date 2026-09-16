package com.lisarios.normaedu.dto.response;

import java.time.LocalDateTime;

public record OrgaoResponse(
        Long id,
        String nome,
        String sigla,
        String descricao,
        Boolean ativo,
        Long organizacaoId,
        String organizacaoNome,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}