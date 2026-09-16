package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.RelacaoNorma;
import com.lisarios.normaedu.dto.response.RelacaoNormaResponse;

public final class RelacaoNormaMapper {

    private RelacaoNormaMapper() {}

    public static RelacaoNormaResponse toResponse(
            RelacaoNorma relacao
    ) {
        return new RelacaoNormaResponse(
                relacao.getId(),

                relacao.getNormaOrigem().getId(),
                relacao.getNormaOrigem().getNumero(),
                relacao.getNormaOrigem().getAno(),

                relacao.getNormaDestino().getId(),
                relacao.getNormaDestino().getNumero(),
                relacao.getNormaDestino().getAno(),

                relacao.getTipoRelacao(),
                relacao.getDispositivo(),
                relacao.getEvidenciaTextual(),
                relacao.getDataRelacao(),
                relacao.getConfirmada(),

                relacao.getCreatedAt(),
                relacao.getUpdatedAt()
        );
    }
}