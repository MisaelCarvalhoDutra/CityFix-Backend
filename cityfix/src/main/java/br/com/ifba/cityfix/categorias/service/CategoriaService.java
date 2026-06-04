package br.com.ifba.cityfix.categorias.service;

import br.com.ifba.cityfix.categorias.dto.CategoriaDTO;
import br.com.ifba.cityfix.categorias.entity.Categoria;
import br.com.ifba.cityfix.categorias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Categoria salvar(CategoriaDTO dto) {

        if(repository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Categoria já cadastrada");
        }

        Categoria categoria = Categoria.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .build();

        return repository.save(categoria);
    }

    public Categoria atualizar(Long id, CategoriaDTO dto) {

        Categoria categoria = buscarPorId(id);

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        return repository.save(categoria);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}