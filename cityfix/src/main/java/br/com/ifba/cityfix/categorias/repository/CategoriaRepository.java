package br.com.ifba.cityfix.categorias.repository;

import br.com.ifba.cityfix.categorias.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNome(String nome);

}