package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.domain.entity.Organizacao;
import com.lisarios.normaedu.dto.request.OrganizacaoRequest;
import com.lisarios.normaedu.dto.response.OrganizacaoResponse;
import com.lisarios.normaedu.mapper.OrganizacaoMapper;
import com.lisarios.normaedu.service.OrganizacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizacoes")
public class OrganizacaoController {

    private final OrganizacaoService organizacaoService;

    public OrganizacaoController(OrganizacaoService organizacaoService) {
        this.organizacaoService = organizacaoService;
    }

    @PostMapping
    public ResponseEntity<OrganizacaoResponse> criar(
            @Valid @RequestBody OrganizacaoRequest request
    ) {
        Organizacao organizacao =
                OrganizacaoMapper.toEntity(request);

        Organizacao salva =
                organizacaoService.criar(organizacao);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrganizacaoMapper.toResponse(salva));
    }

    @GetMapping
    public ResponseEntity<List<OrganizacaoResponse>> listar() {

        List<OrganizacaoResponse> organizacoes =
                organizacaoService.listarTodas()
                        .stream()
                        .map(OrganizacaoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(organizacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizacaoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        Organizacao organizacao =
                organizacaoService.buscarPorId(id);

        return ResponseEntity.ok(
                OrganizacaoMapper.toResponse(organizacao)
        );
    }
}