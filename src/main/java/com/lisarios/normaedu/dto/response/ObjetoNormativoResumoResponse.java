package com.lisarios.normaedu.dto.response;

import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;

public record ObjetoNormativoResumoResponse(
        Long id,
        TipoObjetoNormativo tipo,
        String nome
) {}