package com.lisarios.normaedu.dto.request;

import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.domain.enums.TipoNorma;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NormaRequest(

        @NotNull(message = "O tipo da norma é obrigatório")
        TipoNorma tipo,

        @NotBlank(message = "O número da norma é obrigatório")
        @Size(max = 30, message = "O número deve possuir no máximo 30 caracteres")
        String numero,

        @NotNull(message = "O ano da norma é obrigatório")
        Integer ano,

        @NotBlank(message = "A ementa é obrigatória")
        @Size(max = 1000, message = "A ementa deve possuir no máximo 1000 caracteres")
        String ementa,

        LocalDate dataPublicacao,

        LocalDate dataVigencia,

        StatusNorma status,

        String textoIntegral,

        @Size(max = 255, message = "A fonte deve possuir no máximo 255 caracteres")
        String fonte,

        @Size(max = 1000, message = "A URL da fonte deve possuir no máximo 1000 caracteres")
        String urlFonte

) {}