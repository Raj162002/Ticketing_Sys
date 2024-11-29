package iit.lk.raj.service;

import iit.lk.raj.exception.TicketNotFoundException;
import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    // The TicketRepository is injected into the TicketService (To get the TicketRepository bean)

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public synchronized Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    public synchronized void buyTicket(Customer customer){
        try{
            System.out.println("The name of the thread is: "+Thread.currentThread().getName());
            Ticket tempTicket=findFirstTicketWithFalseStatus();
            tempTicket.setTicketStatus(true);
            tempTicket.setCustomer(customer);
            ticketRepository.save(tempTicket);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ticket with id "+tempTicket.getTicketId()+" has been bought by "+Thread.currentThread().getName());
        }catch(Exception e){
            System.out.println("Error in buying ticket");
            System.out.println(e);

        }
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
    public void changeTicketStatus(Long ticketId, boolean newStatus) {
        try {
            // Fetch the ticket from the database
            Ticket ticket = null;
            try {
                ticket = ticketRepository.findById(ticketId).get();
            } catch (NoSuchElementException e) {
                throw new Exception("Ticket not found", e);
            }

            // Update the ticket's status
            ticket.setTicketStatus(newStatus);

            // Save the updated ticket back to the database
            ticketRepository.save(ticket);

            System.out.println("Ticket status updated successfully.");

        } catch (Exception e) {
            System.out.println("Error while updating ticket status: " + e.getMessage());
        }
    }
    public synchronized Ticket findFirstTicketWithFalseStatus() throws Exception {
       try{
           // Call the repository method to find the first ticket with ticketStatus = false
           List<Ticket> FalseticketList = ticketRepository.findByTicketStatusFalse();

           // If the ticket is present, return it; otherwise, throw an exception
           if (FalseticketList!=null && !(FalseticketList.isEmpty())) {
               return FalseticketList.get(0);
           } else {
               // If no ticket is found, throw a custom exception
               throw new Exception("No ticket with false status found");
           }
       }
        catch (Exception e) {
            // Handle any exceptions that occur during the process
            System.out.println("Error while finding ticket with false status: " + e.getMessage());
            return null;
        }

    }


}
