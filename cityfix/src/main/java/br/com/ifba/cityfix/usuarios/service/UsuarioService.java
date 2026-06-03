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
}