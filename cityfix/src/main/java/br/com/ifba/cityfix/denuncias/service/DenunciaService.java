package br.com.ifba.cityfix.denuncias.service;

import br.com.ifba.cityfix.categorias.entity.Categoria;
import br.com.ifba.cityfix.categorias.service.CategoriaService;
import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import br.com.ifba.cityfix.denuncias.entity.enums.PrioridadeDenuncia;
import br.com.ifba.cityfix.denuncias.entity.enums.StatusDenuncia;
import br.com.ifba.cityfix.denuncias.repository.DenunciaRepository;
import br.com.ifba.cityfix.imagens.entity.ImagemDenuncia;
import br.com.ifba.cityfix.usuarios.entity.Usuario;
import br.com.ifba.cityfix.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.ifba.cityfix.notificacoes.service.NotificacaoService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final DenunciaRepository repository;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    private final NotificacaoService notificacaoService;

    public List<Denuncia> listar() {
        return repository.findAll();
    }

    public Denuncia buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));
    }

    public Denuncia salvar(
            String titulo,
            String descricao,
            String localizacao,
            Long categoriaId,
            Long usuarioId,
            Double latitude,
            Double longitude,
            MultipartFile[] imagens
    ) {
        Categoria categoria = categoriaService.buscarPorId(categoriaId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Denuncia denuncia = Denuncia.builder()
                .titulo(titulo)
                .descricao(descricao)
                .localizacao(localizacao)
                .latitude(latitude)
                .longitude(longitude)
                .categoria(categoria)
                .usuario(usuario)
                .status(StatusDenuncia.ABERTA)
                .prioridade(PrioridadeDenuncia.MEDIA)
                .dataCriacao(LocalDateTime.now())
                .build();

        try {
            if (imagens != null) {
                String pastaUploads = "uploads/";
                Files.createDirectories(Paths.get(pastaUploads));

                for (MultipartFile imagem : imagens) {
                    if (imagem != null && !imagem.isEmpty()) {
                        String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
                        Path caminhoArquivo = Paths.get(pastaUploads + nomeArquivo);

                        Files.write(caminhoArquivo, imagem.getBytes());

                        ImagemDenuncia imagemDenuncia = ImagemDenuncia.builder()
                                .imagemUrl("http://localhost:8080/uploads/" + nomeArquivo)
                                .denuncia(denuncia)
                                .build();

                        denuncia.getImagens().add(imagemDenuncia);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar imagens da denúncia");
        }

        return repository.save(denuncia);
    }

    public Denuncia deletar(Long id) {
        Denuncia denuncia = buscarPorId(id);
        repository.delete(denuncia);
        return denuncia;
    }

    public List<Denuncia> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Denuncia atualizarStatus(
            Long id,
            StatusDenuncia status,
            PrioridadeDenuncia prioridade
    ) {
        Denuncia denuncia = buscarPorId(id);

        StatusDenuncia statusAntigo = denuncia.getStatus();

        denuncia.setStatus(status);
        denuncia.setPrioridade(prioridade);

        Denuncia denunciaAtualizada = repository.save(denuncia);

        if (statusAntigo != status) {
            notificacaoService.criar(
                    denuncia.getUsuario().getId(),
                    "Atualização da denúncia",
                    "Sua denúncia \"" + denuncia.getTitulo() + "\" foi atualizada para "
                            + status.name().replace("_", " ").toLowerCase() + "."
            );
        }

        return denunciaAtualizada;
    }
}