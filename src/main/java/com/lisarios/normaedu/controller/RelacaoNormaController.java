package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.domain.entity.RelacaoNorma;
import com.lisarios.normaedu.dto.request.RelacaoNormaRequest;
import com.lisarios.normaedu.dto.response.RelacaoNormaResponse;
import com.lisarios.normaedu.mapper.RelacaoNormaMapper;
import com.lisarios.normaedu.service.RelacaoNormaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relacoes-normas")
public class RelacaoNormaController {

    private final RelacaoNormaService relacaoService;

    public RelacaoNormaController(
            RelacaoNormaService relacaoService
    ) {
        this.relacaoService = relacaoService;
    }

    @PostMapping("/norma/{normaOrigemId}")
    public ResponseEntity<RelacaoNormaResponse> criar(
            @PathVariable Long normaOrigemId,
            @Valid @RequestBody RelacaoNormaRequest request
    ) {
        RelacaoNorma relacao =
                relacaoService.criar(normaOrigemId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RelacaoNormaMapper.toResponse(relacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelacaoNormaResponse> buscarPorId(
            @PathVariable Long id
    ) {
        RelacaoNorma relacao =
                relacaoService.buscarPorId(id);

        return ResponseEntity.ok(
                RelacaoNormaMapper.toResponse(relacao)
        );
    }

    @GetMapping("/origem/{normaOrigemId}")
    public ResponseEntity<List<RelacaoNormaResponse>> listarPorOrigem(
            @PathVariable Long normaOrigemId
    ) {
        List<RelacaoNormaResponse> relacoes =
                relacaoService.listarPorNormaOrigem(normaOrigemId)
                        .stream()
                        .map(RelacaoNormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(relacoes);
    }

    @GetMapping("/destino/{normaDestinoId}")
    public ResponseEntity<List<RelacaoNormaResponse>> listarPorDestino(
            @PathVariable Long normaDestinoId
    ) {
        List<RelacaoNormaResponse> relacoes =
                relacaoService.listarPorNormaDestino(normaDestinoId)
                        .stream()
                        .map(RelacaoNormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(relacoes);
    }

    @GetMapping("/norma/{normaId}")
    public ResponseEntity<List<RelacaoNormaResponse>> listarDaNorma(
            @PathVariable Long normaId
    ) {
        List<RelacaoNormaResponse> relacoes =
                relacaoService.listarTodasRelacoesDaNorma(normaId)
                        .stream()
                        .map(RelacaoNormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(relacoes);
    }
}