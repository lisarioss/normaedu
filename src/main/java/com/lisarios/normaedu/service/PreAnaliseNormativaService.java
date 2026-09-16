package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.entity.ObjetoNormativo;
import com.lisarios.normaedu.dto.response.PreAnaliseNormativaResponse;
import com.lisarios.normaedu.dto.response.RelacaoNormaResponse;
import com.lisarios.normaedu.mapper.RelacaoNormaMapper;
import com.lisarios.normaedu.repository.NormaRepository;
import com.lisarios.normaedu.repository.RelacaoNormaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreAnaliseNormativaService {

    private final NormaRepository normaRepository;
    private final RelacaoNormaRepository relacaoNormaRepository;
    private final ObjetoNormativoService objetoNormativoService;

    public PreAnaliseNormativaService(
            NormaRepository normaRepository,
            RelacaoNormaRepository relacaoNormaRepository,
            ObjetoNormativoService objetoNormativoService
    ) {
        this.normaRepository = normaRepository;
        this.relacaoNormaRepository = relacaoNormaRepository;
        this.objetoNormativoService = objetoNormativoService;
    }

    public PreAnaliseNormativaResponse analisarPorObjeto(Long objetoId) {

        ObjetoNormativo objeto =
                objetoNormativoService.buscarPorId(objetoId);

        List<Norma> normas =
                normaRepository.findByObjetosId(objetoId);

        List<RelacaoNormaResponse> relacoesConhecidas =
                normas.stream()
                        .flatMap(norma ->
                                relacaoNormaRepository
                                        .findByNormaOrigemId(norma.getId())
                                        .stream()
                        )
                        .filter(relacao ->
                                normas.stream()
                                        .anyMatch(norma ->
                                                norma.getId().equals(
                                                        relacao.getNormaDestino().getId()
                                                )
                                        )
                        )
                        .distinct()
                        .map(RelacaoNormaMapper::toResponse)
                        .toList();

        String resultado;
        String observacao;

        if (normas.size() <= 1) {

            resultado = "SEM_INDICIOS";

            observacao =
                    "Não foram identificadas múltiplas normas "
                    + "relacionadas ao objeto normativo.";

        } else {

            resultado = "REQUER_ANALISE";

            observacao =
                    "Foram identificadas múltiplas normas relacionadas "
                    + "ao mesmo objeto normativo.";
        }

        return new PreAnaliseNormativaResponse(
                objeto.getId(),
                objeto.getNome(),
                normas.size(),
                relacoesConhecidas.size(),
                resultado,
                observacao,
                relacoesConhecidas
        );
    }
}