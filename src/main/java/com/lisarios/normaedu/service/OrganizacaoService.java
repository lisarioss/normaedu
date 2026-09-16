package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Organizacao;
import com.lisarios.normaedu.domain.enums.TipoOrganizacao;
import com.lisarios.normaedu.repository.OrganizacaoRepository;
import org.springframework.stereotype.Service;
import com.lisarios.normaedu.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class OrganizacaoService {

    private final OrganizacaoRepository organizacaoRepository;

    public OrganizacaoService(OrganizacaoRepository organizacaoRepository) {
        this.organizacaoRepository = organizacaoRepository;
    }

    public Organizacao criar(Organizacao organizacao) {
        return organizacaoRepository.save(organizacao);
    }

    public List<Organizacao> listarTodas() {
        return organizacaoRepository.findAll();
    }

    public List<Organizacao> listarAtivas() {
        return organizacaoRepository.findByAtivoTrue();
    }

    public List<Organizacao> buscarPorTipo(TipoOrganizacao tipo) {
        return organizacaoRepository.findByTipo(tipo);
    }

    public Organizacao buscarPorId(Long id) {
        return organizacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Organização não encontrada com id: " + id
                        )
                );
    }
}