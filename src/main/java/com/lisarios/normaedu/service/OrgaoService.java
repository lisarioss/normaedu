package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Orgao;
import com.lisarios.normaedu.domain.entity.Organizacao;
import com.lisarios.normaedu.repository.OrgaoRepository;
import org.springframework.stereotype.Service;
import com.lisarios.normaedu.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class OrgaoService {

    private final OrgaoRepository orgaoRepository;
    private final OrganizacaoService organizacaoService;

    public OrgaoService(
            OrgaoRepository orgaoRepository,
            OrganizacaoService organizacaoService
    ) {
        this.orgaoRepository = orgaoRepository;
        this.organizacaoService = organizacaoService;
    }

    public Orgao criar(Long organizacaoId, Orgao orgao) {
        Organizacao organizacao =
                organizacaoService.buscarPorId(organizacaoId);

        orgao.setOrganizacao(organizacao);

        return orgaoRepository.save(orgao);
    }

    public List<Orgao> listarTodos() {
        return orgaoRepository.findAll();
    }

    public List<Orgao> listarPorOrganizacao(Long organizacaoId) {
        organizacaoService.buscarPorId(organizacaoId);

        return orgaoRepository
                .findByOrganizacaoIdAndAtivoTrue(organizacaoId);
    }

    public Orgao buscarPorId(Long id) {
        return orgaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Órgão não encontrado com id: " + id
                        )
                );
    }
}