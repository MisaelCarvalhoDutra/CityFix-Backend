package br.com.ifba.cityfix.usuarios.entity;

import br.com.ifba.cityfix.usuarios.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // dados básicos do usuário
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String telefone;

    private String cidade;

    // Define se o usuário é comum ou administrador
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;
}