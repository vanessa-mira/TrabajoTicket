package mx.edu.utez.tickets.service;

import mx.edu.utez.tickets.controller.dto.RequestCrearTicketDTO;
import mx.edu.utez.tickets.exception.customException.CustomBadRequestException;
import mx.edu.utez.tickets.exception.customException.CustomNotFoundException;
import mx.edu.utez.tickets.model.ticket.EstadoTicket;
import mx.edu.utez.tickets.model.ticket.PrioridadTicket;
import mx.edu.utez.tickets.model.ticket.Ticket;
import mx.edu.utez.tickets.model.usuario.Usuario;
import mx.edu.utez.tickets.repository.TicketRepository;
import mx.edu.utez.tickets.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Busca el ticket y si no existe manda error 404
    private Ticket buscarTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El ticket no existe"));
    }

    @Transactional
    public Ticket crearTicket(RequestCrearTicketDTO payload) {
        // El usuario debe existir
        Usuario usuario = usuarioRepository.findById(payload.getUsuarioId())
                .orElseThrow(() -> new CustomNotFoundException("El usuario no existe"));

        // El usuario debe estar activo
        if (!usuario.isActivo()) {
            throw new CustomBadRequestException("El usuario no esta activo");
        }

        // La descripcion no debe estar vacia
        if (payload.getDescripcion() == null || payload.getDescripcion().isBlank()) {
            throw new CustomBadRequestException("La descripcion no puede estar vacia");
        }

        Ticket ticket = new Ticket();
        ticket.setDescripcion(payload.getDescripcion().trim());
        ticket.setEstado(EstadoTicket.ABIERTO);
        ticket.setPrioridad(payload.getPrioridad());
        ticket.setUsuario(usuario);
        return ticketRepository.save(ticket);
    }

    // ABIERTO -> EN_PROCESO -> CERRADO. Un ticket CERRADO ya no se puede modificar
    @Transactional
    public Ticket actualizarEstadoTicket(Long id) {
        Ticket ticket = buscarTicket(id);

        if (ticket.getEstado() == EstadoTicket.ABIERTO) {
            ticket.setEstado(EstadoTicket.EN_PROCESO);
        } else if (ticket.getEstado() == EstadoTicket.EN_PROCESO) {
            ticket.setEstado(EstadoTicket.CERRADO);
        } else {
            throw new CustomBadRequestException("El ticket ya esta CERRADO y no se puede modificar");
        }

        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket reasignarPrioridad(Long id, PrioridadTicket prioridad) {
        Ticket ticket = buscarTicket(id);

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new CustomBadRequestException("No se puede cambiar la prioridad de un ticket CERRADO");
        }

        ticket.setPrioridad(prioridad);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new CustomNotFoundException("El usuario no existe");
        }
        return ticketRepository.findByUsuarioId(usuarioId);
    }
}
