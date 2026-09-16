package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.entity.RelacaoNorma;
import com.lisarios.normaedu.dto.request.RelacaoNormaRequest;
import com.lisarios.normaedu.exception.ResourceConflictException;
import com.lisarios.normaedu.exception.ResourceNotFoundException;
import com.lisarios.normaedu.repository.RelacaoNormaRepository;
import org.springframework.stereotype.Service;
import com.lisarios.normaedu.dto.response.HistoricoNormaResponse;
import com.lisarios.normaedu.dto.response.HistoricoRelacaoResponse;

import java.util.List;

@Service
public class RelacaoNormaService {

    private final RelacaoNormaRepository relacaoRepository;
    private final NormaService normaService;

    public RelacaoNormaService(
            RelacaoNormaRepository relacaoRepository,
            NormaService normaService
    ) {
        this.relacaoRepository = relacaoRepository;
        this.normaService = normaService;
    }

    public RelacaoNorma criar(
            Long normaOrigemId,
            RelacaoNormaRequest request
    ) {
        if (normaOrigemId.equals(request.normaDestinoId())) {
            throw new ResourceConflictException(
                    "Uma norma não pode possuir relação normativa com ela mesma"
            );
        }

        boolean relacaoJaExiste =
                relacaoRepository
                        .existsByNormaOrigemIdAndNormaDestinoIdAndTipoRelacao(
                                normaOrigemId,
                                request.normaDestinoId(),
                                request.tipoRelacao()
                        );

        if (relacaoJaExiste) {
            throw new ResourceConflictException(
                    "Esta relação normativa já está cadastrada"
            );
        }

        Norma normaOrigem =
                normaService.buscarPorId(normaOrigemId);

        Norma normaDestino =
                normaService.buscarPorId(request.normaDestinoId());

        RelacaoNorma relacao = RelacaoNorma.builder()
                .normaOrigem(normaOrigem)
                .normaDestino(normaDestino)
                .tipoRelacao(request.tipoRelacao())
                .dispositivo(request.dispositivo())
                .evidenciaTextual(request.evidenciaTextual())
                .dataRelacao(request.dataRelacao())
                .confirmada(
                        Boolean.TRUE.equals(request.confirmada())
                )
                .build();

        return relacaoRepository.save(relacao);
    }

    public RelacaoNorma buscarPorId(Long id) {
        return relacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Relação normativa não encontrada com id: " + id
                        )
                );
    }

    public List<RelacaoNorma> listarPorNormaOrigem(
            Long normaOrigemId
    ) {
        normaService.buscarPorId(normaOrigemId);

        return relacaoRepository
                .findByNormaOrigemId(normaOrigemId);
    }

    public List<RelacaoNorma> listarPorNormaDestino(
            Long normaDestinoId
    ) {
        normaService.buscarPorId(normaDestinoId);

        return relacaoRepository
                .findByNormaDestinoId(normaDestinoId);
    }

    public List<RelacaoNorma> listarTodasRelacoesDaNorma(
            Long normaId
    ) {
        normaService.buscarPorId(normaId);

        return relacaoRepository
                .findByNormaOrigemIdOrNormaDestinoId(
                        normaId,
                        normaId
                );
    }

    public HistoricoNormaResponse montarHistorico(Long normaId) {

        Norma norma = normaService.buscarPorId(normaId);

        List<RelacaoNorma> relacoes =
                relacaoRepository
                        .findByNormaOrigemIdOrNormaDestinoId(
                                normaId,
                                normaId
                        );

        List<HistoricoRelacaoResponse> historico =
                relacoes.stream()
                        .map(relacao -> {

                                boolean normaEhOrigem =
                                        relacao.getNormaOrigem()
                                                .getId()
                                                .equals(normaId);

                                Norma normaRelacionada =
                                        normaEhOrigem
                                                ? relacao.getNormaDestino()
                                                : relacao.getNormaOrigem();

                                String papel =
                                        normaEhOrigem
                                                ? "ORIGEM"
                                                : "DESTINO";

                                return new HistoricoRelacaoResponse(
                                        relacao.getId(),
                                        normaRelacionada.getId(),
                                        normaRelacionada.getNumero(),
                                        normaRelacionada.getAno(),
                                        relacao.getTipoRelacao(),
                                        papel,
                                        relacao.getDispositivo(),
                                        relacao.getEvidenciaTextual(),
                                        relacao.getDataRelacao(),
                                        relacao.getConfirmada()
                                );
                        })
                        .toList();

        return new HistoricoNormaResponse(
                norma.getId(),
                norma.getTipo(),
                norma.getNumero(),
                norma.getAno(),
                norma.getStatus(),
                norma.getEmenta(),
                historico
        );
        }
}