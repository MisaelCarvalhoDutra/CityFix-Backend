package br.com.ifba.cityfix.usuarios.controller;

import br.com.ifba.cityfix.usuarios.entity.Usuario;
import br.com.ifba.cityfix.usuarios.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import br.com.ifba.cityfix.usuarios.dto.LoginRequest;

//Controlador responsável pelos endpoints de usuários.
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") //permite requisições do Front-End para esta API
//ou seja, permite que o Front-End do CityFix acesse os endpoints da API.
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //cadastra um novo usuário
    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return usuarioService.cadastrar(usuario);
    }

    //lista todos os usuários
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    //busca um usuário pelo id
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    //atualiza um usuário
    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable Long id,
                             @RequestBody Usuario usuario) {
        return usuarioService.atualizar(id, usuario);
    }

    //remove um usuário
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
    }

   //realiza o login do usuário
    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest loginRequest) {
        return usuarioService.login(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );
    }
}