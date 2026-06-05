package br.com.ifba.cityfix.denuncias.controller;

import br.com.ifba.cityfix.denuncias.dto.DenunciaDTO;
import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import br.com.ifba.cityfix.denuncias.service.DenunciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.ifba.cityfix.denuncias.entity.enums.StatusDenuncia;
import br.com.ifba.cityfix.denuncias.entity.enums.PrioridadeDenuncia;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DenunciaController {

    private final DenunciaService service;

    @GetMapping
    public List<Denuncia> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Denuncia buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping(consumes = "multipart/form-data")
    public Denuncia salvar(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String localizacao,
            @RequestParam Long categoriaId,
            @RequestParam Long usuarioId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) MultipartFile[] imagens
    ) {
        return service.salvar(
                titulo,
                descricao,
                localizacao,
                categoriaId,
                usuarioId,
                latitude,
                longitude,
                imagens
        );
    }
    @PutMapping("/{id}/status")
    public Denuncia atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusDenuncia status,
            @RequestParam PrioridadeDenuncia prioridade
    ) {
        return service.atualizarStatus(id, status, prioridade);
    }

    @DeleteMapping("/{id}")
    public Denuncia deletar(@PathVariable Long id) {
        return service.deletar(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Denuncia> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }
}