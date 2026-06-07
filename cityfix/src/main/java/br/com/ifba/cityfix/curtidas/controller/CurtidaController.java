package br.com.ifba.cityfix.curtidas.controller;

import br.com.ifba.cityfix.curtidas.dto.CurtidaRequestDTO;
import br.com.ifba.cityfix.curtidas.dto.CurtidaResponseDTO;
import br.com.ifba.cityfix.curtidas.service.CurtidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curtidas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CurtidaController {

    private final CurtidaService curtidaService;

    @PostMapping
    public CurtidaResponseDTO curtir(@RequestBody CurtidaRequestDTO dto) {
        return curtidaService.curtir(dto);
    }

    @DeleteMapping
    public void descurtir(@RequestBody CurtidaRequestDTO dto) {
        curtidaService.descurtir(dto);
    }

    @GetMapping("/denuncia/{denunciaId}/total")
    public Long contarCurtidas(@PathVariable Long denunciaId) {
        return curtidaService.contarPorDenuncia(denunciaId);
    }

    @GetMapping("/denuncia/{denunciaId}/usuario/{usuarioId}")
    public boolean usuarioCurtiu(
            @PathVariable Long denunciaId,
            @PathVariable Long usuarioId
    ) {
        return curtidaService.usuarioCurtiu(usuarioId, denunciaId);
    }
}