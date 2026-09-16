package com.lisarios.normaedu.repository;

import com.lisarios.normaedu.domain.entity.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssuntoRepository extends JpaRepository<Assunto, Long> {

    Optional<Assunto> findByNomeIgnoreCase(String nome);

    List<Assunto> findByAtivoTrueOrderByNomeAsc();
}