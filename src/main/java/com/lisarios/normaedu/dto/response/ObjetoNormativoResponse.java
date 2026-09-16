package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;

import java.time.LocalDateTime;

public record ObjetoNormativoResponse(

        Long id,
        TipoObjetoNormativo tipo,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}