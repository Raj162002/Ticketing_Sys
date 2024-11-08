package iit.lk.raj.service;

import iit.lk.raj.exception.TicketNotFoundException;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    // The TicketRepository is injected into the TicketService (To get the TicketRepository bean)
    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

//Gets all tickets from the database and returns them as a list
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            // This will throw an exception and stop the method here if the ticket does not exist
            throw new TicketNotFoundException(id);
        }

        // The ticket will be deleted since it exists
        ticketRepository.deleteById(id);
        logger.log(Level.INFO, "Successfully deleted ticket with ID: {0}", id);
    }

}
