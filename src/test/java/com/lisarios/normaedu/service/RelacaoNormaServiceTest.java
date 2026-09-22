
package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.entity.Norma;
import com.lisarios.normaedu.domain.entity.RelacaoNorma;
import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;
import com.lisarios.normaedu.exception.ResourceConflictException;
import com.lisarios.normaedu.repository.RelacaoNormaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RelacaoNormaServiceTest {

    private RelacaoNormaRepository repository;
    private RelacaoNormaService service;

    @BeforeEach
    void configurar() {
        repository = mock(RelacaoNormaRepository.class);
        NormaService normaService = mock(NormaService.class);

        service = new RelacaoNormaService(repository, normaService);
    }

    @Test
    void deveCriarRelacaoCandidataNaoConfirmada() {
        Norma origem = Norma.builder().id(41L).build();
        Norma destino = Norma.builder().id(1L).build();

        when(repository.existsByNormaOrigemIdAndNormaDestinoIdAndTipoRelacao(
                41L, 1L, TipoRelacaoNorma.ALTERA
        )).thenReturn(false);

        when(repository.save(any(RelacaoNorma.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RelacaoNorma resultado = service.criarCandidata(
                origem,
                destino,
                TipoRelacaoNorma.ALTERA,
                "Art. 2º Fica alterada a Lei nº 001/2026."
        );

        assertNotNull(resultado);
        assertFalse(resultado.getConfirmada());
        assertEquals(TipoRelacaoNorma.ALTERA, resultado.getTipoRelacao());

        verify(repository).save(any(RelacaoNorma.class));
    }

    @Test
    void naoDeveCriarRelacaoDuplicada() {
        Norma origem = Norma.builder().id(41L).build();
        Norma destino = Norma.builder().id(1L).build();

        when(repository.existsByNormaOrigemIdAndNormaDestinoIdAndTipoRelacao(
                41L, 1L, TipoRelacaoNorma.ALTERA
        )).thenReturn(true);

        RelacaoNorma resultado = service.criarCandidata(
                origem,
                destino,
                TipoRelacaoNorma.ALTERA,
                "Evidência textual"
        );

        assertNull(resultado);
        verify(repository, never()).save(any(RelacaoNorma.class));
    }

    @Test
    void deveConfirmarRelacaoCandidata() {
        RelacaoNorma relacao = RelacaoNorma.builder()
                .confirmada(false)
                .build();

        when(repository.findById(21L))
                .thenReturn(Optional.of(relacao));

        when(repository.save(any(RelacaoNorma.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RelacaoNorma resultado = service.confirmar(21L);

        assertTrue(resultado.getConfirmada());
        verify(repository).save(relacao);
    }

    @Test
    void naoDeveConfirmarRelacaoJaConfirmada() {
        RelacaoNorma relacao = RelacaoNorma.builder()
                .confirmada(true)
                .build();

        when(repository.findById(21L))
                .thenReturn(Optional.of(relacao));

        assertThrows(
                ResourceConflictException.class,
                () -> service.confirmar(21L)
        );

        verify(repository, never()).save(any(RelacaoNorma.class));
    }
}