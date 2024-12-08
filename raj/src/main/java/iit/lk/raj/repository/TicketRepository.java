package iit.lk.raj.repository;

import iit.lk.raj.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.ticketStatus = false AND t.event.id = :eventId")
    List<Ticket> findByTicketStatusFalseAndEventId(@Param("eventId") Long eventId);

    List<Ticket> findByEvent_EventId(Long eventId);

}
