package com.lisarios.normaedu.dto.response;

import java.time.LocalDateTime;

public record AssuntoResponse(

        Long id,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime createdAt

) {}