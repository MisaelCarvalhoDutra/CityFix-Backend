package br.com.ifba.cityfix.usuarios.dto;

import lombok.Getter;
import lombok.Setter;

// Dados necessários para alteração de senha.
@Getter
@Setter
public class AlterarSenhaRequest {

    private String senhaAtual;
    private String novaSenha;
    private String confirmarSenha;
}