package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.Assunto;
import com.lisarios.normaedu.dto.request.AssuntoRequest;
import com.lisarios.normaedu.dto.response.AssuntoResponse;

public final class AssuntoMapper {

    private AssuntoMapper() {}

    public static Assunto toEntity(AssuntoRequest request) {
        return Assunto.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .build();
    }

    public static AssuntoResponse toResponse(Assunto assunto) {
        return new AssuntoResponse(
                assunto.getId(),
                assunto.getNome(),
                assunto.getDescricao(),
                assunto.getAtivo(),
                assunto.getCreatedAt()
        );
    }
}