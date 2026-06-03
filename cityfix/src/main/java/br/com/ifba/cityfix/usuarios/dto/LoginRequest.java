package br.com.ifba.cityfix.usuarios.dto;

import lombok.Getter;
import lombok.Setter;

// dados recebidos na tentativa de login.
@Getter
@Setter
public class LoginRequest {

    private String email;
    private String senha;
}