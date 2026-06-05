package br.com.ifba.cityfix.notificacoes.service;

import br.com.ifba.cityfix.notificacoes.entity.Notificacao;
import br.com.ifba.cityfix.notificacoes.repository.NotificacaoRepository;
import br.com.ifba.cityfix.usuarios.entity.Usuario;
import br.com.ifba.cityfix.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository repository;
    private final UsuarioService usuarioService;

    public List<Notificacao> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);
    }

    public long contarNaoLidas(Long usuarioId) {
        return repository.countByUsuarioIdAndLidaFalse(usuarioId);
    }

    public Notificacao criar(Long usuarioId, String titulo, String mensagem) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Notificacao notificacao = Notificacao.builder()
                .titulo(titulo)
                .mensagem(mensagem)
                .lida(false)
                .dataCriacao(LocalDateTime.now())
                .usuario(usuario)
                .build();

        return repository.save(notificacao);
    }

    public Notificacao marcarComoLida(Long id) {
        Notificacao notificacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));

        notificacao.setLida(true);

        return repository.save(notificacao);
    }

    public void marcarTodasComoLidas(Long usuarioId) {

        List<Notificacao> notificacoes =
                repository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId);

        notificacoes.forEach(n -> n.setLida(true));

        repository.saveAll(notificacoes);
    }
}