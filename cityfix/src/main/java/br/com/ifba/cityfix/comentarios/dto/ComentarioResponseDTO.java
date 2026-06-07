package br.com.ifba.cityfix.comentarios.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ComentarioResponseDTO {

    private Long id;

    private String texto;

    private LocalDateTime dataCriacao;

    private Long usuarioId;

    private String nomeUsuario;

    private Long denunciaId;
}