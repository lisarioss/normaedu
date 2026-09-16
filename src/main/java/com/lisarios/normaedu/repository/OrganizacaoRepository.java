package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.Organizacao;
import com.lisarios.normaedu.domain.enums.TipoOrganizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {

    List<Organizacao> findByAtivoTrue();

    List<Organizacao> findByTipo(TipoOrganizacao tipo);
}