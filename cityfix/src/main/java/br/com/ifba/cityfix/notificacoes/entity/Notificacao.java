package br.com.ifba.cityfix.notificacoes.entity;

import br.com.ifba.cityfix.usuarios.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Representa uma notificação enviada para um usuário.
 */
@Entity
@Table(name = "notificacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título curto da notificação.
     */
    @Column(nullable = false)
    private String titulo;

    /**
     * Mensagem detalhada da notificação.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    /**
     * Indica se o usuário já leu a notificação.
     */
    private Boolean lida;

    /**
     * Data e hora em que a notificação foi criada.
     */
    private LocalDateTime dataCriacao;

    /**
     * Usuário que receberá a notificação.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}