package com.lisarios.normaedu.domain.entity;

import com.lisarios.normaedu.domain.enums.TipoRelacaoNorma;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "relacoes_normas",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_relacao_norma",
            columnNames = {
                "norma_origem_id",
                "norma_destino_id",
                "tipo_relacao"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelacaoNorma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "norma_origem_id", nullable = false)
    private Norma normaOrigem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "norma_destino_id", nullable = false)
    private Norma normaDestino;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_relacao", nullable = false, length = 30)
    private TipoRelacaoNorma tipoRelacao;

    @Column(length = 255)
    private String dispositivo;

    @Column(name = "evidencia_textual", length = 2000)
    private String evidenciaTextual;

    @Column(name = "data_relacao")
    private LocalDate dataRelacao;

    @Builder.Default
    @Column(nullable = false)
    private Boolean confirmada = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        LocalDateTime agora = LocalDateTime.now();

        createdAt = agora;
        updatedAt = agora;

        if (confirmada == null) {
            confirmada = false;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}