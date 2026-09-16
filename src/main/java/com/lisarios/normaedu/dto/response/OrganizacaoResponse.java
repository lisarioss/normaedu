package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoOrganizacao;

import java.time.LocalDateTime;

public record OrganizacaoResponse(
        Long id,
        String nome,
        TipoOrganizacao tipo,
        String cnpj,
        Boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}