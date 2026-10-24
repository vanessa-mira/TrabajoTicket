package mx.edu.utez.tickets.repository;

import mx.edu.utez.tickets.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
