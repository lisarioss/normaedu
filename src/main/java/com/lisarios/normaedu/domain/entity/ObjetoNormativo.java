package com.lisarios.normaedu.domain.entity;

import com.lisarios.normaedu.domain.enums.TipoObjetoNormativo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "objetos_normativos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjetoNormativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoObjetoNormativo tipo;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(length = 1000)
    private String descricao;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        createdAt = agora;
        updatedAt = agora;

        if (ativo == null) {
            ativo = true;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}