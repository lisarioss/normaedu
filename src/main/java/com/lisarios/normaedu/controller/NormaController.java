package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.dto.request.NormaRequest;
import com.lisarios.normaedu.dto.response.HistoricoNormaResponse;
import com.lisarios.normaedu.dto.response.NormaResponse;
import com.lisarios.normaedu.mapper.NormaMapper;
import com.lisarios.normaedu.service.NormaService;
import com.lisarios.normaedu.service.RelacaoNormaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/normas")
public class NormaController {

    private final NormaService normaService;
    private final RelacaoNormaService relacaoNormaService;

    public NormaController(
            NormaService normaService,
            RelacaoNormaService relacaoNormaService
    ) {
        this.normaService = normaService;
        this.relacaoNormaService = relacaoNormaService;
    }

    @PostMapping("/orgao/{orgaoId}")
    public ResponseEntity<NormaResponse> criar(
            @PathVariable Long orgaoId,
            @Valid @RequestBody NormaRequest request
    ) {
        Norma norma = NormaMapper.toEntity(request);
        Norma salva = normaService.criar(orgaoId, norma);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(NormaMapper.toResponse(salva));
    }

    @GetMapping
    public ResponseEntity<List<NormaResponse>> listarTodas() {
        List<NormaResponse> normas =
                normaService.listarTodas()
                        .stream()
                        .map(NormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(normas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NormaResponse> buscarPorId(
            @PathVariable Long id
    ) {
        Norma norma = normaService.buscarPorId(id);

        return ResponseEntity.ok(
                NormaMapper.toResponse(norma)
        );
    }

    @GetMapping("/orgao/{orgaoId}")
    public ResponseEntity<List<NormaResponse>> listarPorOrgao(
            @PathVariable Long orgaoId
    ) {
        List<NormaResponse> normas =
                normaService.listarPorOrgao(orgaoId)
                        .stream()
                        .map(NormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(normas);
    }

    @GetMapping("/orgao/{orgaoId}/status/{status}")
    public ResponseEntity<List<NormaResponse>> listarPorOrgaoEStatus(
            @PathVariable Long orgaoId,
            @PathVariable StatusNorma status
    ) {
        List<NormaResponse> normas =
                normaService.listarPorOrgaoEStatus(
                        orgaoId,
                        status
                )
                        .stream()
                        .map(NormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(normas);
    }

    @PostMapping("/{normaId}/assuntos/{assuntoId}")
    public ResponseEntity<NormaResponse> adicionarAssunto(
            @PathVariable Long normaId,
            @PathVariable Long assuntoId
    ) {
        Norma norma = normaService.adicionarAssunto(
                normaId,
                assuntoId
        );

        return ResponseEntity.ok(
                NormaMapper.toResponse(norma)
        );
    }

    @GetMapping("/assunto/{assuntoId}")
    public ResponseEntity<List<NormaResponse>> listarPorAssunto(
            @PathVariable Long assuntoId
    ) {
        List<NormaResponse> normas =
                normaService.listarPorAssunto(assuntoId)
                        .stream()
                        .map(NormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(normas);
    }

    @PostMapping("/{normaId}/objetos/{objetoId}")
    public ResponseEntity<NormaResponse> adicionarObjeto(
            @PathVariable Long normaId,
            @PathVariable Long objetoId
    ) {
        Norma norma = normaService.adicionarObjeto(
                normaId,
                objetoId
        );

        return ResponseEntity.ok(
                NormaMapper.toResponse(norma)
        );
    }

    @GetMapping("/objeto/{objetoId}")
    public ResponseEntity<List<NormaResponse>> listarPorObjeto(
            @PathVariable Long objetoId
    ) {
        List<NormaResponse> normas =
                normaService.listarPorObjeto(objetoId)
                        .stream()
                        .map(NormaMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(normas);
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<HistoricoNormaResponse> buscarHistorico(
            @PathVariable Long id
    ) {
        HistoricoNormaResponse historico =
                relacaoNormaService.montarHistorico(id);

        return ResponseEntity.ok(historico);
    }
}