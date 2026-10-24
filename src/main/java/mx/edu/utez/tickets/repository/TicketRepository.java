package mx.edu.utez.tickets.repository;

import mx.edu.utez.tickets.model.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUsuarioId(Long usuarioId);
}
