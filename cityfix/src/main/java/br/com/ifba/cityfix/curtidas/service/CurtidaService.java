package br.com.ifba.cityfix.curtidas.service;

import br.com.ifba.cityfix.curtidas.dto.CurtidaRequestDTO;
import br.com.ifba.cityfix.curtidas.dto.CurtidaResponseDTO;
import br.com.ifba.cityfix.curtidas.entity.Curtida;
import br.com.ifba.cityfix.curtidas.repository.CurtidaRepository;
import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import br.com.ifba.cityfix.denuncias.repository.DenunciaRepository;
import br.com.ifba.cityfix.usuarios.entity.Usuario;
import br.com.ifba.cityfix.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurtidaService {

    private final CurtidaRepository curtidaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DenunciaRepository denunciaRepository;

    public CurtidaResponseDTO curtir(CurtidaRequestDTO dto) {
        if (curtidaRepository.existsByUsuarioIdAndDenunciaId(dto.getUsuarioId(), dto.getDenunciaId())) {
            throw new RuntimeException("Usuário já curtiu essa denúncia");
        }

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Denuncia denuncia = denunciaRepository.findById(dto.getDenunciaId())
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

        Curtida curtida = new Curtida();
        curtida.setUsuario(usuario);
        curtida.setDenuncia(denuncia);

        Curtida salva = curtidaRepository.save(curtida);

        return CurtidaResponseDTO.builder()
                .id(salva.getId())
                .usuarioId(salva.getUsuario().getId())
                .denunciaId(salva.getDenuncia().getId())
                .build();
    }

    @Transactional
    public void descurtir(CurtidaRequestDTO dto) {
        curtidaRepository.deleteByUsuarioIdAndDenunciaId(dto.getUsuarioId(), dto.getDenunciaId());
    }

    public Long contarPorDenuncia(Long denunciaId) {
        return curtidaRepository.countByDenunciaId(denunciaId);
    }

    public boolean usuarioCurtiu(Long usuarioId, Long denunciaId) {
        return curtidaRepository.existsByUsuarioIdAndDenunciaId(usuarioId, denunciaId);
    }
}