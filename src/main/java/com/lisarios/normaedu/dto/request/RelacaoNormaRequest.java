package com.lisarios.normaedu.dto.request;

import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RelacaoNormaRequest(

        @NotNull(message = "A norma de destino é obrigatória")
        Long normaDestinoId,

        @NotNull(message = "O tipo da relação é obrigatório")
        TipoRelacaoNorma tipoRelacao,

        @Size(max = 255, message = "O dispositivo deve possuir no máximo 255 caracteres")
        String dispositivo,

        @Size(max = 2000, message = "A evidência textual deve possuir no máximo 2000 caracteres")
        String evidenciaTextual,

        LocalDate dataRelacao,

        Boolean confirmada

) {}