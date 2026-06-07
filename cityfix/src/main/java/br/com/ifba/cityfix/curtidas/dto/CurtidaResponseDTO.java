package br.com.ifba.cityfix.curtidas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurtidaResponseDTO {

    private Long id;

    private Long usuarioId;

    private Long denunciaId;
}