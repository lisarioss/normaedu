package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.AlertaNormativo;
import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.entity.ObjetoNormativo;
import com.lisarios.normaedu.domain.enums.StatusAlertaNormativo;
import com.lisarios.normaedu.domain.enums.TipoAlertaNormativo;
import com.lisarios.normaedu.exception.ResourceConflictException;
import com.lisarios.normaedu.exception.ResourceNotFoundException;
import com.lisarios.normaedu.repository.AlertaNormativoRepository;
import com.lisarios.normaedu.repository.NormaRepository;
import org.springframework.stereotype.Service;
import com.lisarios.normaedu.dto.request.AtualizarAlertaRequest;
import com.lisarios.normaedu.exception.ResourceConflictException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaNormativoService {

    private final AlertaNormativoRepository alertaRepository;
    private final NormaRepository normaRepository;
    private final ObjetoNormativoService objetoNormativoService;

    public AlertaNormativoService(
            AlertaNormativoRepository alertaRepository,
            NormaRepository normaRepository,
            ObjetoNormativoService objetoNormativoService
    ) {
        this.alertaRepository = alertaRepository;
        this.normaRepository = normaRepository;
        this.objetoNormativoService = objetoNormativoService;
    }

    public List<AlertaNormativo> detectarPorObjeto(Long objetoId) {

        ObjetoNormativo objeto =
                objetoNormativoService.buscarPorId(objetoId);

        List<Norma> normas =
                normaRepository.findByObjetosId(objetoId);

        List<AlertaNormativo> alertasCriados =
                new ArrayList<>();

        for (int i = 0; i < normas.size(); i++) {

            for (int j = i + 1; j < normas.size(); j++) {

                Norma primeira = normas.get(i);
                Norma segunda = normas.get(j);

                Norma normaA =
                        primeira.getId() < segunda.getId()
                                ? primeira
                                : segunda;

                Norma normaB =
                        primeira.getId() < segunda.getId()
                                ? segunda
                                : primeira;

                boolean alertaJaExiste =
                        alertaRepository
                                .existsByObjetoNormativoIdAndNormaAIdAndNormaBId(
                                        objetoId,
                                        normaA.getId(),
                                        normaB.getId()
                                );

                if (alertaJaExiste) {
                    continue;
                }

                AlertaNormativo alerta =
                        AlertaNormativo.builder()
                                .tipo(
                                        TipoAlertaNormativo
                                                .MULTIPLAS_NORMAS_MESMO_OBJETO
                                )
                                .status(
                                        StatusAlertaNormativo.PENDENTE
                                )
                                .motivo(
                                        "Foram encontradas múltiplas normas "
                                                + "relacionadas ao mesmo objeto normativo. "
                                                + "A situação requer análise."
                                )
                                .objetoNormativo(objeto)
                                .normaA(normaA)
                                .normaB(normaB)
                                .build();

                alertasCriados.add(
                        alertaRepository.save(alerta)
                );
            }
        }

        return alertasCriados;
    }

    public List<AlertaNormativo> listarTodos() {
        return alertaRepository.findAll();
    }

    public List<AlertaNormativo> listarPendentes() {
        return alertaRepository.findByStatus(
                StatusAlertaNormativo.PENDENTE
        );
    }

    public List<AlertaNormativo> listarPorObjeto(Long objetoId) {
        objetoNormativoService.buscarPorId(objetoId);

        return alertaRepository
                .findByObjetoNormativoId(objetoId);
    }

    public AlertaNormativo buscarPorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Alerta normativo não encontrado com id: " + id
                        )
                );
    }

    public AlertaNormativo atualizar(
        Long alertaId,
        AtualizarAlertaRequest request
        ) {
                AlertaNormativo alerta = buscarPorId(alertaId);

                StatusAlertaNormativo statusAtual = alerta.getStatus();
                StatusAlertaNormativo novoStatus = request.status();

                validarTransicaoStatus(statusAtual, novoStatus);

                alerta.setStatus(novoStatus);

                if (request.evidencia() != null
                        && !request.evidencia().isBlank()) {

                        alerta.setEvidencia(
                                request.evidencia().trim()
                        );
                }

                return alertaRepository.save(alerta);
        }

        private void validarTransicaoStatus(
                StatusAlertaNormativo atual,
                StatusAlertaNormativo novo
        ) {
        if (atual == novo) {
                return;
        }

        boolean transicaoValida = switch (atual) {

                case PENDENTE ->
                        novo == StatusAlertaNormativo.EM_ANALISE
                                || novo == StatusAlertaNormativo.DESCARTADO;

                case EM_ANALISE ->
                        novo == StatusAlertaNormativo.CONFIRMADO
                                || novo == StatusAlertaNormativo.DESCARTADO;

                case CONFIRMADO, DESCARTADO -> false;
        };

        if (!transicaoValida) {
                throw new ResourceConflictException(
                        "Transição de status inválida: "
                                + atual
                                + " -> "
                                + novo
                );
        }
        }
}