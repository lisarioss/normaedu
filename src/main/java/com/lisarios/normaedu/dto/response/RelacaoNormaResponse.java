package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RelacaoNormaResponse(

        Long id,

        Long normaOrigemId,
        String normaOrigemNumero,
        Integer normaOrigemAno,

        Long normaDestinoId,
        String normaDestinoNumero,
        Integer normaDestinoAno,

        TipoRelacaoNorma tipoRelacao,

        String dispositivo,
        String evidenciaTextual,
        LocalDate dataRelacao,
        Boolean confirmada,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {}