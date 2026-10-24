package mx.edu.utez.tickets.controller.dto;

import jakarta.validation.constraints.NotNull;
import mx.edu.utez.tickets.model.ticket.PrioridadTicket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPrioridadDTO {

    @NotNull(message = "La prioridad es obligatoria (BAJA, MEDIA o ALTA)")
    private PrioridadTicket prioridad;
}
