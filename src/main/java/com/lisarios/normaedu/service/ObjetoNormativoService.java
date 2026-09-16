package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.ObjetoNormativo;
import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;
import com.lisarios.normaedu.exception.ResourceNotFoundException;
import com.lisarios.normaedu.repository.ObjetoNormativoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjetoNormativoService {

    private final ObjetoNormativoRepository objetoRepository;

    public ObjetoNormativoService(
            ObjetoNormativoRepository objetoRepository
    ) {
        this.objetoRepository = objetoRepository;
    }

    public ObjetoNormativo criar(ObjetoNormativo objeto) {
        objeto.setNome(objeto.getNome().trim());

        return objetoRepository.save(objeto);
    }

    public List<ObjetoNormativo> listarTodos() {
        return objetoRepository.findAll();
    }

    public List<ObjetoNormativo> listarAtivos() {
        return objetoRepository.findByAtivoTrueOrderByNomeAsc();
    }

    public List<ObjetoNormativo> listarPorTipo(
            TipoObjetoNormativo tipo
    ) {
        return objetoRepository.findByTipoAndAtivoTrue(tipo);
    }

    public ObjetoNormativo buscarPorId(Long id) {
        return objetoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Objeto normativo não encontrado com id: " + id
                        )
                );
    }
}