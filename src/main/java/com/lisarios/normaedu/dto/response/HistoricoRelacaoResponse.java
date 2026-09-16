package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;

import java.time.LocalDate;

public record HistoricoRelacaoResponse(
        Long relacaoId,
        Long normaRelacionadaId,
        String normaRelacionadaNumero,
        Integer normaRelacionadaAno,
        TipoRelacaoNorma tipoRelacao,
        String papel,
        String dispositivo,
        String evidenciaTextual,
        LocalDate dataRelacao,
        Boolean confirmada
) {
}