package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.dto.response.ReferenciaNormativaAnalisadaResponse;
import com.lisarios.normaedu.dto.response.ReferenciaNormativaDetectadaResponse;
import com.lisarios.normaedu.repository.NormaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnaliseReferenciaNormativaService {

    private final NormaRepository normaRepository;

    public AnaliseReferenciaNormativaService(
            NormaRepository normaRepository
    ) {
        this.normaRepository = normaRepository;
    }

    public List<ReferenciaNormativaAnalisadaResponse> analisar(
            List<ReferenciaNormativaDetectadaResponse> referencias,
            Long orgaoId
    ) {

        return referencias.stream()
                .map(referencia -> analisarReferencia(referencia, orgaoId))
                .toList();
    }

    private ReferenciaNormativaAnalisadaResponse analisarReferencia(
            ReferenciaNormativaDetectadaResponse referencia,
            Long orgaoId
    ) {

        Optional<Norma> normaEncontrada =
                normaRepository.findByTipoAndNumeroAndAnoAndOrgaoId(
                        referencia.tipoNorma(),
                        referencia.numero(),
                        referencia.ano(),
                        orgaoId
                );

        return new ReferenciaNormativaAnalisadaResponse(
                referencia.tipoRelacao(),
                referencia.tipoNorma(),
                referencia.numero(),
                referencia.ano(),
                referencia.evidenciaTextual(),
                normaEncontrada.isPresent(),
                normaEncontrada
                        .map(norma -> norma.getId())
                        .orElse(null)
        );
    }
}