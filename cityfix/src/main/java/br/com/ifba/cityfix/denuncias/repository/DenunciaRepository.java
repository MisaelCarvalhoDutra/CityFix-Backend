package br.com.ifba.cityfix.denuncias.repository;

import br.com.ifba.cityfix.denuncias.entity.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    List<Denuncia> findByUsuarioId(Long usuarioId);
}