package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.Organizacao;
import com.lisarios.normaedu.dto.request.OrganizacaoRequest;
import com.lisarios.normaedu.dto.response.OrganizacaoResponse;

public final class OrganizacaoMapper {

    private OrganizacaoMapper() {
    }

    public static Organizacao toEntity(OrganizacaoRequest request) {
        return Organizacao.builder()
                .nome(request.nome())
                .tipo(request.tipo())
                .cnpj(request.cnpj())
                .build();
    }

    public static OrganizacaoResponse toResponse(Organizacao organizacao) {
        return new OrganizacaoResponse(
                organizacao.getId(),
                organizacao.getNome(),
                organizacao.getTipo(),
                organizacao.getCnpj(),
                organizacao.getAtivo(),
                organizacao.getCreatedAt(),
                organizacao.getUpdatedAt()
        );
    }
}