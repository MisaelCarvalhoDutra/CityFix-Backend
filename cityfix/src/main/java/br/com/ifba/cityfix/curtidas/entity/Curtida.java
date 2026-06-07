package br.com.ifba.cityfix.curtidas.entity;

import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import br.com.ifba.cityfix.usuarios.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "curtidas",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "denuncia_id"})
        }
)
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "denuncia_id", nullable = false)
    private Denuncia denuncia;
}