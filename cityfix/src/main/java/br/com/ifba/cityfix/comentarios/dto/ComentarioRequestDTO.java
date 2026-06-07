package br.com.ifba.cityfix.comentarios.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioRequestDTO {

    private String texto;

    private Long usuarioId;

    private Long denunciaId;
}