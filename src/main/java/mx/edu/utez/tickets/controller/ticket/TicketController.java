package mx.edu.utez.tickets.controller.ticket;

import jakarta.validation.Valid;
import mx.edu.utez.tickets.controller.dto.RequestCrearTicketDTO;
import mx.edu.utez.tickets.controller.dto.RequestPrioridadDTO;
import mx.edu.utez.tickets.model.ticket.Ticket;
import mx.edu.utez.tickets.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin({"*"})
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody @Valid RequestCrearTicketDTO payload) {
        return ResponseEntity.status(201).body(ticketService.crearTicket(payload));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Ticket> actualizarEstadoTicket(@PathVariable Long id) {
        return ResponseEntity.status(200).body(ticketService.actualizarEstadoTicket(id));
    }

    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<Ticket> reasignarPrioridad(@PathVariable Long id,
                                                     @RequestBody @Valid RequestPrioridadDTO payload) {
        return ResponseEntity.status(200).body(ticketService.reasignarPrioridad(id, payload.getPrioridad()));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> obtenerTicketsUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.status(200).body(ticketService.obtenerTicketsUsuario(usuarioId));
    }
}
