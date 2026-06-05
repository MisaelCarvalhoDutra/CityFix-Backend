package br.com.ifba.cityfix.denuncias.entity;

import br.com.ifba.cityfix.categorias.entity.Categoria;
import br.com.ifba.cityfix.denuncias.entity.enums.PrioridadeDenuncia;
import br.com.ifba.cityfix.denuncias.entity.enums.StatusDenuncia;
import br.com.ifba.cityfix.usuarios.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;

import br.com.ifba.cityfix.imagens.entity.ImagemDenuncia;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

/**
 * Representa uma denúncia registrada por um cidadão.
 */
@Entity
@Table(name = "denuncias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título resumido da denúncia.
     */
    @Column(nullable = false)
    private String titulo;

    /**
     * Descrição detalhada do problema.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    /**
     * Endereço ou localização informada.
     */
    @Column(nullable = false)
    private String localizacao;

    /**
     * Data e hora do registro.
     */
    private LocalDateTime dataCriacao;

    /**
     * Status atual da denúncia.
     */
    @Enumerated(EnumType.STRING)
    private StatusDenuncia status;

    /**
     * Prioridade atribuída pela administração.
     */
    @Enumerated(EnumType.STRING)
    private PrioridadeDenuncia prioridade;

    /**
     * Categoria da denúncia.
     */
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    /**
     * Usuário que registrou a denúncia.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "denuncia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ImagemDenuncia> imagens = new ArrayList<>();


    private Double latitude;
    private Double longitude;
}