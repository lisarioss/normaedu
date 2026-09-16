package com.lisarios.normaedu.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;

@Service
public class DocumentoService {

    public void validarPdf(MultipartFile arquivo) {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException(
                    "O arquivo PDF é obrigatório"
            );
        }

        String nomeArquivo = arquivo.getOriginalFilename();

        if (nomeArquivo == null
                || !nomeArquivo.toLowerCase().endsWith(".pdf")) {

            throw new IllegalArgumentException(
                    "O arquivo deve possuir extensão PDF"
            );
        }

        String contentType = arquivo.getContentType();

        if (contentType != null
                && !contentType.equalsIgnoreCase("application/pdf")) {

            throw new IllegalArgumentException(
                    "O tipo do arquivo deve ser application/pdf"
            );
        }
    }

    public String extrairTexto(MultipartFile arquivo) {

        validarPdf(arquivo);

        try (PDDocument documento =
                    Loader.loadPDF(arquivo.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(documento).trim();

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Não foi possível ler o conteúdo do PDF"
            );
        }
    }
} 