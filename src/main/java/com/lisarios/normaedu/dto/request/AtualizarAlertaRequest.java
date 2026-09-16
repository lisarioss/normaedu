package com.lisarios.normaedu.dto.request;

import com.lisarios.normaedu.domain.enums.StatusAlertaNormativo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarAlertaRequest(

        @NotNull(message = "O status do alerta é obrigatório")
        StatusAlertaNormativo status,

        @Size(
                max = 2000,
                message = "A evidência deve possuir no máximo 2000 caracteres"
        )
        String evidencia

) {
}