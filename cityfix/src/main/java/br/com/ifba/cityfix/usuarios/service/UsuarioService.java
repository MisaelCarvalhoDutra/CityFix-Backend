package br.com.ifba.cityfix.usuarios.service;

import br.com.ifba.cityfix.usuarios.entity.Usuario;
import br.com.ifba.cityfix.usuarios.enums.TipoUsuario;
import br.com.ifba.cityfix.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //cadastra um novo usuário no sistema
    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        if (usuario.getTipoUsuario() == null) {
            usuario.setTipoUsuario(TipoUsuario.USUARIO);
        }

        return usuarioRepository.save(usuario);
    }

    // lista todos os usuários cadastrados
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    //faz a busca de um usuário pelo ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    //atualiza os dados básicos do usuário
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setTelefone(usuarioAtualizado.getTelefone());
        usuario.setCidade(usuarioAtualizado.getCidade());

        return usuarioRepository.save(usuario);
    }

    //remove um usuário do sistema
    public void deletar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    // valida o login do usuário pelo e-mail e senha.
    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null || !usuario.getSenha().equals(senha)) {
            throw new RuntimeException("E-mail ou senha inválidos.");
        }

        return usuario;
    }

    // Altera a senha somente se a senha atual estiver correta.
    public Usuario alterarSenha(Long id, String senhaAtual, String novaSenha, String confirmarSenha) {
        Usuario usuario = buscarPorId(id);

        if (!usuario.getSenha().equals(senhaAtual)) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        if (!novaSenha.equals(confirmarSenha)) {
            throw new RuntimeException("As senhas não coincidem.");
        }

        if (novaSenha.length() < 8) {
            throw new RuntimeException("A nova senha deve ter no mínimo 8 caracteres.");
        }

        usuario.setSenha(novaSenha);

        return usuarioRepository.save(usuario);
    }
}