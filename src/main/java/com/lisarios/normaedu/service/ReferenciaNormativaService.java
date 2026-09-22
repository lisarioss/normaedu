package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.enums.TipoNorma;
import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;
import com.lisarios.normaedu.dto.response.ReferenciaNormativaDetectadaResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReferenciaNormativaService {

    private static final String REFERENCIA_LEI =
            "\\blei\\s*(?:n[º°o.]?\\s*)?(\\d+)\\s*/\\s*(\\d{4})";

    private static final Pattern PADRAO_REVOGACAO_PARCIAL = Pattern.compile(
            "(?i)([^\\r\\n]*?\\b(?:revoga|revogado|revogada|revogados|revogadas)\\b"
                    + "[^\\r\\n]*?\\b(?:art\\.?|artigo|artigos)\\b"
                    + "[^\\r\\n]*?" + REFERENCIA_LEI + "[^\\r\\n]*)"
    );

    private static final Pattern PADRAO_REVOGACAO = Pattern.compile(
            "(?i)([^\\r\\n]*?\\b(?:revoga|revogada|revogado)\\b"
                    + "[^\\r\\n]*?" + REFERENCIA_LEI + "[^\\r\\n]*)"
    );

    private static final Pattern PADRAO_ALTERACAO = Pattern.compile(
            "(?i)([^\\r\\n]*?\\b(?:altera|alterada|alterado)\\b"
                    + "[^\\r\\n]*?" + REFERENCIA_LEI + "[^\\r\\n]*)"
    );

    private static final Pattern PADRAO_REGULAMENTACAO = Pattern.compile(
            "(?i)([^\\r\\n]*?\\b(?:regulamenta|regulamentada|regulamentado)\\b"
                    + "[^\\r\\n]*?" + REFERENCIA_LEI + "[^\\r\\n]*)"
    );

    private static final Pattern PADRAO_COMPLEMENTACAO = Pattern.compile(
            "(?i)([^\\r\\n]*?\\b(?:complementa|complementada|complementado)\\b"
                    + "[^\\r\\n]*?" + REFERENCIA_LEI + "[^\\r\\n]*)"
    );

    private static final Pattern PADRAO_SUBSTITUICAO = Pattern.compile(
            "(?i)([^\\r\\n]*?\\b(?:substitui|substituída|substituida|substituído|substituido)\\b"
                    + "[^\\r\\n]*?" + REFERENCIA_LEI + "[^\\r\\n]*)"
    );

    private static final Pattern PADRAO_IDENTIFICACAO_NORMA = Pattern.compile(
        "(?i)\\b(LEI|DECRETO|PORTARIA|RESOLUÇÃO|RESOLUCAO|INSTRUÇÃO NORMATIVA|INSTRUCAO NORMATIVA)"
                + "\\s*(?:n[º°o.]?\\s*)?(\\d+)\\s*/\\s*(\\d{4})"
    );

    public List<ReferenciaNormativaDetectadaResponse> detectar(String texto) {

        List<ReferenciaNormativaDetectadaResponse> referencias =
                new ArrayList<>();

        if (texto == null || texto.isBlank()) {
            return referencias;
        }

        detectarPorPadrao(
                texto,
                PADRAO_REVOGACAO_PARCIAL,
                TipoRelacaoNorma.REVOGA_PARCIALMENTE,
                referencias
        );

        detectarPorPadrao(
                texto,
                PADRAO_REVOGACAO,
                TipoRelacaoNorma.REVOGA,
                referencias
        );

        detectarPorPadrao(
                texto,
                PADRAO_ALTERACAO,
                TipoRelacaoNorma.ALTERA,
                referencias
        );

        detectarPorPadrao(
                texto,
                PADRAO_REGULAMENTACAO,
                TipoRelacaoNorma.REGULAMENTA,
                referencias
        );

        detectarPorPadrao(
                texto,
                PADRAO_COMPLEMENTACAO,
                TipoRelacaoNorma.COMPLEMENTA,
                referencias
        );

        detectarPorPadrao(
                texto,
                PADRAO_SUBSTITUICAO,
                TipoRelacaoNorma.SUBSTITUI,
                referencias
        );

        return referencias;
    }

    private void detectarPorPadrao(
            String texto,
            Pattern padrao,
            TipoRelacaoNorma tipoRelacao,
            List<ReferenciaNormativaDetectadaResponse> referencias
    ) {

        Matcher matcher = padrao.matcher(texto);

        while (matcher.find()) {

            String evidencia = matcher.group(1).trim();
            String numero = matcher.group(2);
            Integer ano = Integer.valueOf(matcher.group(3));

            referencias.add(
                    new ReferenciaNormativaDetectadaResponse(
                            tipoRelacao,
                            TipoNorma.LEI,
                            numero,
                            ano,
                            evidencia
                    )
            );
        }
    }

    public ReferenciaNormativaDetectadaResponse identificarNorma(
        String texto
        ) {
        if (texto == null || texto.isBlank()) {
                throw new IllegalArgumentException(
                        "Não foi possível identificar a norma no documento"
                );
        }

        Matcher matcher = PADRAO_IDENTIFICACAO_NORMA.matcher(texto);

        if (!matcher.find()) {
                throw new IllegalArgumentException(
                        "Não foi possível identificar tipo, número e ano da norma no documento"
                );
        }

        TipoNorma tipoNorma = converterTipoNorma(matcher.group(1));
        String numero = matcher.group(2);
        Integer ano = Integer.valueOf(matcher.group(3));

        return new ReferenciaNormativaDetectadaResponse(
                null,
                tipoNorma,
                numero,
                ano,
                matcher.group().trim()
        );
        }

        private TipoNorma converterTipoNorma(String valor) {
                String normalizado = valor
                        .toUpperCase()
                        .replace("Ç", "C")
                        .replace("Ã", "A");

                return switch (normalizado) {
                        case "LEI" -> TipoNorma.LEI;
                        case "DECRETO" -> TipoNorma.DECRETO;
                        case "PORTARIA" -> TipoNorma.PORTARIA;
                        case "RESOLUCAO" -> TipoNorma.RESOLUCAO;
                        case "INSTRUCAO NORMATIVA" -> TipoNorma.INSTRUCAO_NORMATIVA;
                        default -> TipoNorma.OUTRO;
                };
        }
}