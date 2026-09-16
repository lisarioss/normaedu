package com.lisarios.normaedu.mapper;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.dto.request.NormaRequest;
import com.lisarios.normaedu.dto.response.AssuntoResumoResponse;
import com.lisarios.normaedu.dto.response.NormaResponse;
import com.lisarios.normaedu.dto.response.ObjetoNormativoResumoResponse;

import java.util.stream.Collectors;

public final class NormaMapper {

    private NormaMapper() {}

    public static Norma toEntity(NormaRequest request) {
        return Norma.builder()
                .tipo(request.tipo())
                .numero(request.numero())
                .ano(request.ano())
                .ementa(request.ementa())
                .dataPublicacao(request.dataPublicacao())
                .dataVigencia(request.dataVigencia())
                .status(request.status())
                .textoIntegral(request.textoIntegral())
                .fonte(request.fonte())
                .urlFonte(request.urlFonte())
                .build();
    }

    public static NormaResponse toResponse(Norma norma) {

        var assuntos = norma.getAssuntos()
                .stream()
                .map(assunto ->
                        new AssuntoResumoResponse(
                                assunto.getId(),
                                assunto.getNome()
                        )
                )
                .collect(Collectors.toSet());

        var objetos = norma.getObjetos()
                .stream()
                .map(objeto ->
                        new ObjetoNormativoResumoResponse(
                                objeto.getId(),
                                objeto.getTipo(),
                                objeto.getNome()
                        )
                )
                .collect(Collectors.toSet());

        return new NormaResponse(
                norma.getId(),
                norma.getTipo(),
                norma.getNumero(),
                norma.getAno(),
                norma.getEmenta(),
                norma.getDataPublicacao(),
                norma.getDataVigencia(),
                norma.getStatus(),
                norma.getTextoIntegral(),
                norma.getFonte(),
                norma.getUrlFonte(),
                norma.getOrgao().getId(),
                norma.getOrgao().getNome(),
                assuntos,
                objetos,
                norma.getCreatedAt(),
                norma.getUpdatedAt()
        );
    }
}