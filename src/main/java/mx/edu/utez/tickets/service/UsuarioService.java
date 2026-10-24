package mx.edu.utez.tickets.service;

import mx.edu.utez.tickets.controller.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.tickets.exception.customException.CustomBadRequestException;
import mx.edu.utez.tickets.exception.customException.CustomNotFoundException;
import mx.edu.utez.tickets.model.usuario.Usuario;
import mx.edu.utez.tickets.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(RequestCrearUsuarioDTO payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre(payload.getNombre());
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    // desactivarUsuario: solo cambia activo a false, no elimina datos
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El usuario no existe"));

        if (!usuario.isActivo()) {
            throw new CustomBadRequestException("El usuario ya esta desactivado");
        }

        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }
}
