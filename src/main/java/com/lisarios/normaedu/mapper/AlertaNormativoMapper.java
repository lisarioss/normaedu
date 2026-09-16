package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.AlertaNormativo;
import com.lisarios.normaedu.dto.response.AlertaNormativoResponse;

public final class AlertaNormativoMapper {

    private AlertaNormativoMapper() {
    }

    public static AlertaNormativoResponse toResponse(
            AlertaNormativo alerta
    ) {
        return new AlertaNormativoResponse(
                alerta.getId(),
                alerta.getTipo(),
                alerta.getStatus(),
                alerta.getMotivo(),

                alerta.getObjetoNormativo().getId(),
                alerta.getObjetoNormativo().getNome(),

                alerta.getNormaA().getId(),
                alerta.getNormaA().getNumero(),
                alerta.getNormaA().getAno(),

                alerta.getNormaB().getId(),
                alerta.getNormaB().getNumero(),
                alerta.getNormaB().getAno(),

                alerta.getEvidencia(),
                alerta.getCreatedAt(),
                alerta.getUpdatedAt()
        );
    }
}