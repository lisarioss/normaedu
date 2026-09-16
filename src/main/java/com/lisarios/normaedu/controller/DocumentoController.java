package com.lisarios.normaedu.controller;

import com.lisarios.normaedu.dto.response.DocumentoPdfResponse;
import com.lisarios.normaedu.dto.response.ReferenciaNormativaDetectadaResponse;
import com.lisarios.normaedu.service.DocumentoService;
import com.lisarios.normaedu.service.ReferenciaNormativaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;
    private final ReferenciaNormativaService referenciaNormativaService;

    public DocumentoController(
            DocumentoService documentoService,
            ReferenciaNormativaService referenciaNormativaService
    ) {
        this.documentoService = documentoService;
        this.referenciaNormativaService = referenciaNormativaService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<DocumentoPdfResponse> receberPdf(
            @RequestParam("arquivo") MultipartFile arquivo
    ) {

        String texto = documentoService.extrairTexto(arquivo);

        List<ReferenciaNormativaDetectadaResponse> referencias =
                referenciaNormativaService.detectar(texto);

        DocumentoPdfResponse response =
                new DocumentoPdfResponse(
                        arquivo.getOriginalFilename(),
                        texto,
                        referencias
                );

        return ResponseEntity.ok(response);
    }
}