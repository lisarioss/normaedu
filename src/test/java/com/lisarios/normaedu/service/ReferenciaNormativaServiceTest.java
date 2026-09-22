
package com.lisarios.normaedu.service;

import com.lisarios.normaedu.domain.enums.TipoNorma;
import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReferenciaNormativaServiceTest {

    private final ReferenciaNormativaService service =
            new ReferenciaNormativaService();

    @Test
    void deveIdentificarNormaNoDocumento() {
        var resultado = service.identificarNorma(
                "LEI Nº 003/2026\nDispõe sobre a organização escolar."
        );

        assertEquals(TipoNorma.LEI, resultado.tipoNorma());
        assertEquals("003", resultado.numero());
        assertEquals(2026, resultado.ano());
    }

    @Test
    void deveDetectarAlteracaoDeLei() {
        var referencias = service.detectar(
                "Art. 2º Fica alterada a Lei nº 001/2026."
        );

        assertEquals(1, referencias.size());

        var referencia = referencias.get(0);

        assertEquals(TipoRelacaoNorma.ALTERA, referencia.tipoRelacao());
        assertEquals(TipoNorma.LEI, referencia.tipoNorma());
        assertEquals("001", referencia.numero());
        assertEquals(2026, referencia.ano());
    }

    @Test
    void naoDeveDetectarReferenciaEmTextoVazio() {
        assertTrue(service.detectar("").isEmpty());
        assertTrue(service.detectar(null).isEmpty());
    }

    @Test
    void deveRejeitarDocumentoSemIdentificacaoDaNorma() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.identificarNorma(
                        "Documento sem tipo, número e ano."
                )
        );
    }
}