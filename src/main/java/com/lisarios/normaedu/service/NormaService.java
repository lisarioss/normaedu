package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Assunto;
import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.entity.Orgao;
import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.exception.ResourceNotFoundException;
import com.lisarios.normaedu.repository.NormaRepository;
import org.springframework.stereotype.Service;
import com.lisarios.normaedu.domain.entity.ObjetoNormativo;

import java.util.List;

@Service
public class NormaService {

    private final NormaRepository normaRepository;
    private final OrgaoService orgaoService;
    private final AssuntoService assuntoService;
    private final ObjetoNormativoService objetoNormativoService;

    public NormaService(
            NormaRepository normaRepository,
            OrgaoService orgaoService,
            AssuntoService assuntoService,
            ObjetoNormativoService objetoNormativoService
    ) {
        this.normaRepository = normaRepository;
        this.orgaoService = orgaoService;
        this.assuntoService = assuntoService;
        this.objetoNormativoService = objetoNormativoService;
    }

    public Norma criar(Long orgaoId, Norma norma) {
        Orgao orgao = orgaoService.buscarPorId(orgaoId);

        norma.setOrgao(orgao);

        if (norma.getStatus() == null) {
            norma.setStatus(StatusNorma.SEM_INFORMACAO);
        }

        return normaRepository.save(norma);
    }

    public List<Norma> listarTodas() {
        return normaRepository.findAll();
    }

    public Norma buscarPorId(Long id) {
        return normaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Norma não encontrada com id: " + id
                        )
                );
    }

    public List<Norma> listarPorOrgao(Long orgaoId) {
        orgaoService.buscarPorId(orgaoId);

        return normaRepository.findByOrgaoId(orgaoId);
    }

    public List<Norma> listarPorOrgaoEStatus(
            Long orgaoId,
            StatusNorma status
    ) {
        orgaoService.buscarPorId(orgaoId);

        return normaRepository.findByOrgaoIdAndStatus(
                orgaoId,
                status
        );
    }

    public Norma adicionarAssunto(Long normaId, Long assuntoId) {
        Norma norma = buscarPorId(normaId);
        Assunto assunto = assuntoService.buscarPorId(assuntoId);

        norma.getAssuntos().add(assunto);

        return normaRepository.save(norma);
    }

    public List<Norma> listarPorAssunto(Long assuntoId) {
        assuntoService.buscarPorId(assuntoId);

        return normaRepository.findByAssuntosId(assuntoId);
    }

    public Norma adicionarObjeto(Long normaId, Long objetoId) {
        Norma norma = buscarPorId(normaId);

        ObjetoNormativo objeto =
                objetoNormativoService.buscarPorId(objetoId);

        norma.getObjetos().add(objeto);

        return normaRepository.save(norma);
    }

        public List<Norma> listarPorObjeto(Long objetoId) {
        objetoNormativoService.buscarPorId(objetoId);

        return normaRepository.findByObjetosId(objetoId);
    }
}