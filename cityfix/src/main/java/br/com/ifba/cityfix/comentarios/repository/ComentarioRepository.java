package br.com.ifba.cityfix.comentarios.repository;

import br.com.ifba.cityfix.comentarios.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByDenunciaIdOrderByDataCriacaoDesc(Long denunciaId);

    Long countByDenunciaId(Long denunciaId);
}