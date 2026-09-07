package br.com.pjerj.teste.repository;

import br.com.pjerj.teste.entity.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {

    Optional<TipoUsuario> findByOrigem(String origem);
}
