package br.com.pjerj.teste.repository;

import br.com.pjerj.teste.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
