package br.com.ifba.cityfix.curtidas.repository;

import br.com.ifba.cityfix.curtidas.entity.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {

    boolean existsByUsuarioIdAndDenunciaId(Long usuarioId, Long denunciaId);

    Long countByDenunciaId(Long denunciaId);

    void deleteByUsuarioIdAndDenunciaId(Long usuarioId, Long denunciaId);
}