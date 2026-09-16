package com.lisarios.normaedu.dto.request;

import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ObjetoNormativoRequest(

        @NotNull(message = "O tipo do objeto normativo é obrigatório")
        TipoObjetoNormativo tipo,

        @NotBlank(message = "O nome do objeto normativo é obrigatório")
        @Size(max = 255, message = "O nome deve possuir no máximo 255 caracteres")
        String nome,

        @Size(max = 1000, message = "A descrição deve possuir no máximo 1000 caracteres")
        String descricao

) {}