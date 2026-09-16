package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrgaoRepository extends JpaRepository<Orgao, Long> {

    List<Orgao> findByOrganizacaoId(Long organizacaoId);

    List<Orgao> findByOrganizacaoIdAndAtivoTrue(Long organizacaoId);
}