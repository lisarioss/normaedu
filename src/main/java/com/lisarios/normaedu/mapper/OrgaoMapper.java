package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.Orgao;
import com.lisarios.normaedu.dto.request.OrgaoRequest;
import com.lisarios.normaedu.dto.response.OrgaoResponse;

public final class OrgaoMapper {

    private OrgaoMapper() {
    }

    public static Orgao toEntity(OrgaoRequest request) {
        return Orgao.builder()
                .nome(request.nome())
                .sigla(request.sigla())
                .descricao(request.descricao())
                .build();
    }

    public static OrgaoResponse toResponse(Orgao orgao) {
        return new OrgaoResponse(
                orgao.getId(),
                orgao.getNome(),
                orgao.getSigla(),
                orgao.getDescricao(),
                orgao.getAtivo(),
                orgao.getOrganizacao().getId(),
                orgao.getOrganizacao().getNome(),
                orgao.getCreatedAt(),
                orgao.getUpdatedAt()
        );
    }
}
