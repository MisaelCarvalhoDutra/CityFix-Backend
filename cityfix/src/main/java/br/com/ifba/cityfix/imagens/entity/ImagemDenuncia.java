package br.com.ifba.cityfix.imagens.entity;

import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagens_denuncia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagemDenuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "denuncia_id", nullable = false)
    @JsonIgnore
    private Denuncia denuncia;
}