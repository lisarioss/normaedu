package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.domain.entity.ObjetoNormativo;
import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;
import com.lisarios.normaedu.dto.request.ObjetoNormativoRequest;
import com.lisarios.normaedu.dto.response.ObjetoNormativoResponse;
import com.lisarios.normaedu.mapper.ObjetoNormativoMapper;
import com.lisarios.normaedu.service.ObjetoNormativoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/objetos")
public class ObjetoNormativoController {

    private final ObjetoNormativoService objetoService;

    public ObjetoNormativoController(
            ObjetoNormativoService objetoService
    ) {
        this.objetoService = objetoService;
    }

    @PostMapping
    public ResponseEntity<ObjetoNormativoResponse> criar(
            @Valid @RequestBody ObjetoNormativoRequest request
    ) {
        ObjetoNormativo objeto =
                ObjetoNormativoMapper.toEntity(request);

        ObjetoNormativo salvo =
                objetoService.criar(objeto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ObjetoNormativoMapper.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ObjetoNormativoResponse>> listarTodos() {
        List<ObjetoNormativoResponse> objetos =
                objetoService.listarTodos()
                        .stream()
                        .map(ObjetoNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(objetos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<ObjetoNormativoResponse>> listarAtivos() {
        List<ObjetoNormativoResponse> objetos =
                objetoService.listarAtivos()
                        .stream()
                        .map(ObjetoNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(objetos);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ObjetoNormativoResponse>> listarPorTipo(
            @PathVariable TipoObjetoNormativo tipo
    ) {
        List<ObjetoNormativoResponse> objetos =
                objetoService.listarPorTipo(tipo)
                        .stream()
                        .map(ObjetoNormativoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(objetos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjetoNormativoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        ObjetoNormativo objeto =
                objetoService.buscarPorId(id);

        return ResponseEntity.ok(
                ObjetoNormativoMapper.toResponse(objeto)
        );
    }
}