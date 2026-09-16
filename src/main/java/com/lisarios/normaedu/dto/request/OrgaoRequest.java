package com.lisarios.normaedu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrgaoRequest(

        @NotBlank(message = "O nome do órgão é obrigatório")
        @Size(max = 150, message = "O nome deve possuir no máximo 150 caracteres")
        String nome,

        @Size(max = 30, message = "A sigla deve possuir no máximo 30 caracteres")
        String sigla,

        @Size(max = 500, message = "A descrição deve possuir no máximo 500 caracteres")
        String descricao

) {
}