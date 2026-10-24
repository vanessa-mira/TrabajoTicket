package mx.edu.utez.tickets.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.tickets.controller.dto.RequestCrearUsuarioDTO;
import mx.edu.utez.tickets.model.usuario.Usuario;
import mx.edu.utez.tickets.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin({"*"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody @Valid RequestCrearUsuarioDTO payload) {
        return ResponseEntity.status(201).body(usuarioService.crearUsuario(payload));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(200).body(usuarioService.desactivarUsuario(id));
    }
}
