package com.lisarios.normaedu.domain.entity;

import com.lisarios.normaedu.domain.enums.StatusNorma;
import com.lisarios.normaedu.domain.enums.TipoNorma;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "normas",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_norma_tipo_numero_ano_orgao",
            columnNames = {"tipo", "numero", "ano", "orgao_id"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Norma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoNorma tipo;

    @Column(nullable = false, length = 30)
    private String numero;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 1000)
    private String ementa;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "data_vigencia")
    private LocalDate dataVigencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private StatusNorma status = StatusNorma.SEM_INFORMACAO;

    @Lob
    @Column(name = "texto_integral")
    private String textoIntegral;

    @Column(length = 255)
    private String fonte;

    @Column(name = "url_fonte", length = 1000)
    private String urlFonte;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orgao_id", nullable = false)
    private Orgao orgao;

    @ManyToMany
@JoinTable(
    name = "normas_assuntos",
    joinColumns = @JoinColumn(name = "norma_id"),
    inverseJoinColumns = @JoinColumn(name = "assunto_id")
)
    @Builder.Default
    private Set<Assunto> assuntos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "normas_objetos",
        joinColumns = @JoinColumn(name = "norma_id"),
        inverseJoinColumns = @JoinColumn(name = "objeto_id")
    )
    @Builder.Default
    private Set<ObjetoNormativo> objetos = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        LocalDateTime agora = LocalDateTime.now();

        createdAt = agora;
        updatedAt = agora;

        if (status == null) {
            status = StatusNorma.SEM_INFORMACAO;
        }
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}