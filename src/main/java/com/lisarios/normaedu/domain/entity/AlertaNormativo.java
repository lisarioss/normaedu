package com.lisarios.normaedu.domain.entity;

import com.lisarios.normaedu.domain.enums.StatusAlertaNormativo;
import com.lisarios.normaedu.domain.enums.TipoAlertaNormativo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas_normativos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaNormativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoAlertaNormativo tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private StatusAlertaNormativo status =
            StatusAlertaNormativo.PENDENTE;

    @Column(nullable = false, length = 1000)
    private String motivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "objeto_normativo_id", nullable = false)
    private ObjetoNormativo objetoNormativo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "norma_a_id", nullable = false)
    private Norma normaA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "norma_b_id", nullable = false)
    private Norma normaB;

    @Column(length = 2000)
    private String evidencia;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;

        if (status == null) {
            status = StatusAlertaNormativo.PENDENTE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}