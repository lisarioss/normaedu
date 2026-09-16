package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Assunto;
import com.lisarios.normaedu.exception.ResourceConflictException;
import com.lisarios.normaedu.exception.ResourceNotFoundException;
import com.lisarios.normaedu.repository.AssuntoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public Assunto criar(Assunto assunto) {
        String nomeNormalizado = assunto.getNome().trim();

        assuntoRepository.findByNomeIgnoreCase(nomeNormalizado)
                .ifPresent(assuntoExistente -> {
                    throw new ResourceConflictException(
                            "Já existe um assunto cadastrado com o nome: "
                                    + nomeNormalizado
                    );
                });

        assunto.setNome(nomeNormalizado);

        return assuntoRepository.save(assunto);
    }

    public List<Assunto> listarTodos() {
        return assuntoRepository.findAll();
    }

    public List<Assunto> listarAtivos() {
        return assuntoRepository.findByAtivoTrueOrderByNomeAsc();
    }

    public Assunto buscarPorId(Long id) {
        return assuntoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assunto não encontrado com id: " + id
                        )
                );
    }
}