package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.dto.response.AlertaNormativoResponse;
import com.lisarios.normaedu.mapper.AlertaNormativoMapper;
import com.lisarios.normaedu.service.AlertaNormativoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lisarios.normaedu.domain.entity.AlertaNormativo;
import com.lisarios.normaedu.dto.request.AtualizarAlertaRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
public class AlertaNormativoController {

    private final AlertaNormativoService alertaService;

    public AlertaNormativoController(
            AlertaNormativoService alertaService
    ) {
        this.alertaService = alertaService;
    }

    @PostMapping("/detectar/objeto/{objetoId}")
    public ResponseEntity<List<AlertaNormativoResponse>> detectarPorObjeto(
            @PathVariable Long objetoId
    ) {
        List<AlertaNormativoResponse> alertas =
                alertaService.detectarPorObjeto(objetoId)
                        .stream()
                        .map(AlertaNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(alertas);
    }

    @GetMapping
    public ResponseEntity<List<AlertaNormativoResponse>> listarTodos() {
        List<AlertaNormativoResponse> alertas =
                alertaService.listarTodos()
                        .stream()
                        .map(AlertaNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<AlertaNormativoResponse>> listarPendentes() {
        List<AlertaNormativoResponse> alertas =
                alertaService.listarPendentes()
                        .stream()
                        .map(AlertaNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/objeto/{objetoId}")
    public ResponseEntity<List<AlertaNormativoResponse>> listarPorObjeto(
            @PathVariable Long objetoId
    ) {
        List<AlertaNormativoResponse> alertas =
                alertaService.listarPorObjeto(objetoId)
                        .stream()
                        .map(AlertaNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaNormativoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                AlertaNormativoMapper.toResponse(
                        alertaService.buscarPorId(id)
                )
        );
    }

    @PatchMapping("/{id}")
        public ResponseEntity<AlertaNormativoResponse> atualizar(
                @PathVariable Long id,
                @Valid @RequestBody AtualizarAlertaRequest request
        ) {
        AlertaNormativo alerta =
                alertaService.atualizar(id, request);

        return ResponseEntity.ok(
                AlertaNormativoMapper.toResponse(alerta)
        );
        }
}