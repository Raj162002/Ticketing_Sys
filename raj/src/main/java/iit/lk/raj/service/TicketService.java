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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());
    private final Lock ticketLock = new ReentrantLock();


    // The TicketRepository is injected into the TicketService (To get the TicketRepository bean)

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public synchronized Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
//    public synchronized void buyTicket(Customer customer){
//        try{
//            System.out.println("The name of the thread is: "+Thread.currentThread().getName());
//            tempTicket=findFirstTicketWithFalseStatus();
//            if(tempTicket==null){
//                System.out.println("No tickets available at buy ticket");
//                wait();
//            }
//            tempTicket.setTicketStatus(true);
//            tempTicket.setCustomer(customer);
//            ticketRepository.save(tempTicket);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Ticket with id "+tempTicket.getTicketId()+" has been bought by "+Thread.currentThread().getName());
//            notifyAll();
//        }catch(Exception e){
//            System.out.println("Error in buying ticket");
//            System.out.println(e);
//
//        }
//    }

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
    public synchronized void changeTicketStatus(Long ticketId, boolean newStatus) {
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
        List<Ticket> falseticketList = ticketRepository.findByTicketStatusFalse();
        System.out.println("The list is going to be printed in "+Thread.currentThread().getName());
        if(falseticketList.isEmpty()){
            System.out.println("The list is empty");
        }
        for (Ticket ticket : falseticketList) {
            System.out.println(ticket);
        }

        if (falseticketList != null && !falseticketList.isEmpty()) {
            return falseticketList.get(0);
        } else {
            return null; // No available ticket, return null
        }
    }
    public void buyTicket2(Customer customer) {
        try {
            // Find the first available ticket with false status
            Ticket tempTicket = findFirstTicketWithFalseStatus();

            // Wait if no ticket is available
            while (tempTicket == null) {
                try {
                    System.out.println("No tickets available, waiting...");
                    wait();  // Wait for tickets to be available

                    // Recheck ticket availability after being notified
                    tempTicket = findFirstTicketWithFalseStatus();
                } catch (InterruptedException e) {
                    // Proper handling of interruption
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // Double-check ticket status to prevent race conditions
            if (tempTicket.isTicketStatus() == false) {
                tempTicket.setTicketStatus(true);
                tempTicket.setCustomer(customer);
                ticketRepository.save(tempTicket);

                // Simulate processing delay
                Thread.sleep(1000);

                System.out.println("Ticket with id " + tempTicket.getTicketId() + " has been bought by " + Thread.currentThread().getName());

                // Notify all waiting threads that a ticket was bought
                notifyAll();
            } else {
                // If ticket was already taken, wait again
                wait();
            }

        } catch (Exception e) {
            System.out.println("Error in buying ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void buyTicket3(Customer customer) {
        try {
            // Acquire the lock to ensure thread-safe access
            ticketLock.lock();

            try {
                // Find available tickets
                List<Ticket> availableTickets = ticketRepository.findByTicketStatusFalse();

                // Check if tickets are available
                if (availableTickets.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + ": No tickets available");
                    return;
                }

                // Get the first available ticket
                Ticket ticketToBuy = availableTickets.get(0);

                // Double-check ticket status (additional safety)
                if (!ticketToBuy.isTicketStatus()) {
                    // Mark ticket as bought
                    ticketToBuy.setTicketStatus(true);
                    ticketToBuy.setCustomer(customer);

                    // Save the updated ticket
                    ticketRepository.save(ticketToBuy);

                    // Log successful booking
                    System.out.println("Ticket with id " + ticketToBuy.getTicketId() +
                            " has been bought by " + Thread.currentThread().getName());
                }
            } finally {
                // Always release the lock
                ticketLock.unlock();
            }
        } catch (Exception e) {
            System.out.println("Error in buying ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }







}
