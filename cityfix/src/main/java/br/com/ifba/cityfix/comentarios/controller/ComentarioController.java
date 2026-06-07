package br.com.ifba.cityfix.comentarios.controller;

import br.com.ifba.cityfix.comentarios.dto.ComentarioRequestDTO;
import br.com.ifba.cityfix.comentarios.dto.ComentarioResponseDTO;
import br.com.ifba.cityfix.comentarios.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ComentarioResponseDTO salvar(@RequestBody ComentarioRequestDTO dto) {
        return comentarioService.salvar(dto);
    }

    @GetMapping("/denuncia/{denunciaId}")
    public List<ComentarioResponseDTO> listarPorDenuncia(@PathVariable Long denunciaId) {
        return comentarioService.listarPorDenuncia(denunciaId);
    }

    @GetMapping("/denuncia/{denunciaId}/total")
    public Long contarPorDenuncia(@PathVariable Long denunciaId) {
        return comentarioService.contarPorDenuncia(denunciaId);
    }

    @PutMapping("/{comentarioId}")
    public ComentarioResponseDTO editar(
            @PathVariable Long comentarioId,
            @RequestBody ComentarioRequestDTO dto
    ) {
        return comentarioService.editar(comentarioId, dto);
    }

    @DeleteMapping("/{comentarioId}/usuario/{usuarioId}")
    public void deletar(
            @PathVariable Long comentarioId,
            @PathVariable Long usuarioId
    ) {
        comentarioService.deletar(comentarioId, usuarioId);
    }
}