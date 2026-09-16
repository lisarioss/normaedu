package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.StatusAlertaNormativo;
import com.lisarios.normaedu.domain.enums.TipoAlertaNormativo;

import java.time.LocalDateTime;

public record AlertaNormativoResponse(
        Long id,
        TipoAlertaNormativo tipo,
        StatusAlertaNormativo status,
        String motivo,
        Long objetoId,
        String objetoNome,
        Long normaAId,
        String normaANumero,
        Integer normaAAno,
        Long normaBId,
        String normaBNumero,
        Integer normaBAno,
        String evidencia,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}