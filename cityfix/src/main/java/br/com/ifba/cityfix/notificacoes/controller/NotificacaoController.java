package br.com.ifba.cityfix.notificacoes.controller;

import br.com.ifba.cityfix.notificacoes.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import br.com.ifba.cityfix.notificacoes.entity.Notificacao;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificacaoController {

    private final NotificacaoService service;

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacao> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public long contarNaoLidas(@PathVariable Long usuarioId) {
        return service.contarNaoLidas(usuarioId);
    }

    @PutMapping("/{id}/lida")
    public Notificacao marcarComoLida(@PathVariable Long id) {
        return service.marcarComoLida(id);
    }

    @PutMapping("/usuario/{usuarioId}/lidas")
    public void marcarTodasComoLidas(
            @PathVariable Long usuarioId
    ) {
        service.marcarTodasComoLidas(usuarioId);
    }
}