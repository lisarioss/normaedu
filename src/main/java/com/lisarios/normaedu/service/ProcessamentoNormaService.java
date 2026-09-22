package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.dto.response.ProcessamentoNormaPdfResponse;
import com.lisarios.normaedu.dto.response.ReferenciaNormativaAnalisadaResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProcessamentoNormaService {

    private final NormaService normaService;
    private final RelacaoNormaService relacaoNormaService;

    public ProcessamentoNormaService(
            NormaService normaService,
            RelacaoNormaService relacaoNormaService
    ) {
        this.normaService = normaService;
        this.relacaoNormaService = relacaoNormaService;
    }

    public ProcessamentoNormaPdfResponse processarPdf(
            Long normaId,
            MultipartFile arquivo
    ) {
        ProcessamentoNormaPdfResponse processamento =
                normaService.processarPdf(normaId, arquivo);

        Norma normaOrigem =
                normaService.buscarPorId(normaId);

        List<ReferenciaNormativaAnalisadaResponse> referencias =
                processamento.referenciasAnalisadas();

        for (ReferenciaNormativaAnalisadaResponse referencia : referencias) {

            if (!referencia.normaEncontrada()
                    || referencia.normaId() == null) {
                continue;
            }

            Norma normaDestino =
                    normaService.buscarPorId(referencia.normaId());

                    relacaoNormaService.criarCandidata(
                            normaOrigem,
                            normaDestino,
                            referencia.tipoRelacao(),
                            referencia.evidenciaTextual()
                    );
        }

        return processamento;
    }
}