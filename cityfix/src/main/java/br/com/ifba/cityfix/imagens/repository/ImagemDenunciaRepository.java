package br.com.ifba.cityfix.imagens.repository;

import br.com.ifba.cityfix.imagens.entity.ImagemDenuncia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagemDenunciaRepository extends JpaRepository<ImagemDenuncia, Long> {
}