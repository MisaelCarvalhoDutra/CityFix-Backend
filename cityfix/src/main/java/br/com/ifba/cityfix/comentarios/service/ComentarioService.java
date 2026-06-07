package br.com.ifba.cityfix.comentarios.service;

import br.com.ifba.cityfix.comentarios.dto.ComentarioRequestDTO;
import br.com.ifba.cityfix.comentarios.dto.ComentarioResponseDTO;
import br.com.ifba.cityfix.comentarios.entity.Comentario;
import br.com.ifba.cityfix.comentarios.repository.ComentarioRepository;
import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import br.com.ifba.cityfix.denuncias.repository.DenunciaRepository;
import br.com.ifba.cityfix.usuarios.entity.Usuario;
import br.com.ifba.cityfix.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final DenunciaRepository denunciaRepository;

    public ComentarioResponseDTO salvar(ComentarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Denuncia denuncia = denunciaRepository.findById(dto.getDenunciaId())
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

        Comentario comentario = new Comentario();
        comentario.setTexto(dto.getTexto());
        comentario.setUsuario(usuario);
        comentario.setDenuncia(denuncia);

        Comentario salvo = comentarioRepository.save(comentario);

        return toResponseDTO(salvo);
    }

    public List<ComentarioResponseDTO> listarPorDenuncia(Long denunciaId) {
        return comentarioRepository.findByDenunciaIdOrderByDataCriacaoDesc(denunciaId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Long contarPorDenuncia(Long denunciaId) {
        return comentarioRepository.countByDenunciaId(denunciaId);
    }

    private ComentarioResponseDTO toResponseDTO(Comentario comentario) {
        return ComentarioResponseDTO.builder()
                .id(comentario.getId())
                .texto(comentario.getTexto())
                .dataCriacao(comentario.getDataCriacao())
                .usuarioId(comentario.getUsuario().getId())
                .nomeUsuario(comentario.getUsuario().getNome())
                .denunciaId(comentario.getDenuncia().getId())
                .build();
    }

    public ComentarioResponseDTO editar(Long comentarioId, ComentarioRequestDTO dto) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

        if (!comentario.getUsuario().getId().equals(dto.getUsuarioId())) {
            throw new RuntimeException("Você só pode editar seus próprios comentários");
        }

        comentario.setTexto(dto.getTexto());

        return toResponseDTO(comentarioRepository.save(comentario));
    }

    public void deletar(Long comentarioId, Long usuarioId) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        boolean dono = comentario.getUsuario().getId().equals(usuarioId);
        boolean admin = usuario.getTipoUsuario().name().equals("ADMINISTRADOR");

        if (!dono && !admin) {
            throw new RuntimeException("Você não tem permissão para apagar este comentário");
        }

        comentarioRepository.delete(comentario);
    }
}