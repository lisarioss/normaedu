package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.domain.entity.Orgao;
import com.lisarios.normaedu.dto.request.OrgaoRequest;
import com.lisarios.normaedu.dto.response.OrgaoResponse;
import com.lisarios.normaedu.mapper.OrgaoMapper;
import com.lisarios.normaedu.service.OrgaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orgaos")
public class OrgaoController {

    private final OrgaoService orgaoService;

    public OrgaoController(OrgaoService orgaoService) {
        this.orgaoService = orgaoService;
    }

    @PostMapping("/organizacao/{organizacaoId}")
    public ResponseEntity<OrgaoResponse> criar(
            @PathVariable Long organizacaoId,
            @Valid @RequestBody OrgaoRequest request
    ) {

        Orgao orgao = OrgaoMapper.toEntity(request);

        Orgao salvo = orgaoService.criar(
                organizacaoId,
                orgao
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrgaoMapper.toResponse(salvo));
    }

    @GetMapping
    public ResponseEntity<List<OrgaoResponse>> listarTodos() {

        List<OrgaoResponse> orgaos =
                orgaoService.listarTodos()
                        .stream()
                        .map(OrgaoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(orgaos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrgaoResponse> buscarPorId(
            @PathVariable Long id
    ) {

        Orgao orgao = orgaoService.buscarPorId(id);

        return ResponseEntity.ok(
                OrgaoMapper.toResponse(orgao)
        );
    }

    @GetMapping("/organizacao/{organizacaoId}")
    public ResponseEntity<List<OrgaoResponse>> listarPorOrganizacao(
            @PathVariable Long organizacaoId
    ) {

        List<OrgaoResponse> orgaos =
                orgaoService.listarPorOrganizacao(organizacaoId)
                        .stream()
                        .map(OrgaoMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(orgaos);
    }
}