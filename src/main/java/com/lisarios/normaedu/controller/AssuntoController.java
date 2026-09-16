package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.domain.entity.Assunto;
import com.lisarios.normaedu.dto.request.AssuntoRequest;
import com.lisarios.normaedu.dto.response.AssuntoResponse;
import com.lisarios.normaedu.mapper.AssuntoMapper;
import com.lisarios.normaedu.service.AssuntoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assuntos")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @PostMapping
    public ResponseEntity<AssuntoResponse> criar(
            @Valid @RequestBody AssuntoRequest request
    ) {
        Assunto assunto = AssuntoMapper.toEntity(request);
        Assunto salvo = assuntoService.criar(assunto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AssuntoMapper.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<AssuntoResponse>> listarTodos() {
        List<AssuntoResponse> assuntos =
                assuntoService.listarTodos()
                        .stream()
                        .map(AssuntoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(assuntos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<AssuntoResponse>> listarAtivos() {
        List<AssuntoResponse> assuntos =
                assuntoService.listarAtivos()
                        .stream()
                        .map(AssuntoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(assuntos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssuntoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        Assunto assunto = assuntoService.buscarPorId(id);

        return ResponseEntity.ok(
                AssuntoMapper.toResponse(assunto)
        );
    }
}