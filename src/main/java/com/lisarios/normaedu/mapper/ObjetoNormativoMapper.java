package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.ObjetoNormativo;
import com.lisarios.normaedu.dto.request.ObjetoNormativoRequest;
import com.lisarios.normaedu.dto.response.ObjetoNormativoResponse;

public final class ObjetoNormativoMapper {

    private ObjetoNormativoMapper() {}

    public static ObjetoNormativo toEntity(
            ObjetoNormativoRequest request
    ) {
        return ObjetoNormativo.builder()
                .tipo(request.tipo())
                .nome(request.nome())
                .descricao(request.descricao())
                .build();
    }

    public static ObjetoNormativoResponse toResponse(
            ObjetoNormativo objeto
    ) {
        return new ObjetoNormativoResponse(
                objeto.getId(),
                objeto.getTipo(),
                objeto.getNome(),
                objeto.getDescricao(),
                objeto.getAtivo(),
                objeto.getCreatedAt(),
                objeto.getUpdatedAt()
        );
    }
}