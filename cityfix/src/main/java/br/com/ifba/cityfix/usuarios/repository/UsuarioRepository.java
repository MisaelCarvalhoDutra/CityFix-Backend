package br.com.ifba.cityfix.usuarios.repository;

import br.com.ifba.cityfix.usuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Responsável pelas operações de acesso aos dados de usuários.
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //faz a busca de um usuário pelo e-mail.
    Usuario findByEmail(String email);

    //faz a verificação se já existe um usuário com o e-mail informado.
    boolean existsByEmail(String email);
}