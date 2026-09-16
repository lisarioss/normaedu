package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.dto.response.PreAnaliseNormativaResponse;
import com.lisarios.normaedu.service.PreAnaliseNormativaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pre-analises")
public class PreAnaliseNormativaController {

    private final PreAnaliseNormativaService preAnaliseService;

    public PreAnaliseNormativaController(
            PreAnaliseNormativaService preAnaliseService
    ) {
        this.preAnaliseService = preAnaliseService;
    }

    @GetMapping("/objeto/{objetoId}")
    public ResponseEntity<PreAnaliseNormativaResponse> analisarPorObjeto(
            @PathVariable Long objetoId
    ) {
        return ResponseEntity.ok(
                preAnaliseService.analisarPorObjeto(objetoId)
        );
    }
}