package com.lisarios.normaedu.dto.request;

import com.lisarios.normaedu.domain.enums.TipoOrganizacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrganizacaoRequest(

        @NotBlank(message = "O nome da organização é obrigatório")
        @Size(max = 150, message = "O nome deve possuir no máximo 150 caracteres")
        String nome,

        @NotNull(message = "O tipo da organização é obrigatório")
        TipoOrganizacao tipo,

        @Size(max = 14, message = "O CNPJ deve possuir no máximo 14 caracteres")
        String cnpj

) {
}