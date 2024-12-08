package iit.lk.raj.service;

import iit.lk.raj.controller.websocket.TicketUpdate;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketUpdateService {

    private final TicketRepository ticketRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public TicketUpdateService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Method to send all ticket data for a specific event
    public void sendAllTicketsForEvent(Long eventId) {
        List<Ticket> tickets = ticketRepository.findByEvent_EventId(eventId);

        // Convert the ticket list to a simplified format and send it
        tickets.forEach(ticket -> {
            String status = ticket.isTicketStatus() ? "Sold" : "Unsold";
            TicketUpdate updateMessage = new TicketUpdate(
                    ticket.getTicketId(),
                    ticket.getEvent().getEventId(),
                    ticket.getVendor().getVendorId(),
                    ticket.getCustomer().getCustomerId(), //this will return customerId it can be null too
                    ticket.isTicketStatus() //This will return a boolean
            );
            // Send the update to the WebSocket topic
            messagingTemplate.convertAndSend("/topic/tickets", updateMessage);
        });
    }
}
