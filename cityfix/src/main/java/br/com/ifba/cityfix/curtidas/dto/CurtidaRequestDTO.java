package br.com.ifba.cityfix.curtidas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurtidaRequestDTO {

    private Long usuarioId;

    private Long denunciaId;
}