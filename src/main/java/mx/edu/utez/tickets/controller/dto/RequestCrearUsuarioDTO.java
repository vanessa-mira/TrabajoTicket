package mx.edu.utez.tickets.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCrearUsuarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
}
