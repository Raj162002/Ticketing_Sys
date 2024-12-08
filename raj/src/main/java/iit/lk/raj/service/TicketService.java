package iit.lk.raj.service;

import iit.lk.raj.exception.TicketNotFoundException;
import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());
    private final Lock ticketLock = new ReentrantLock();
    private int customerRetrivalRate;
    private int ticketRetrivalRate;
    @Autowired
    private TicketUpdateService ticketUpdateService;
    @Autowired
    private ApplicationContext applicationContext;

    public int getTicketRetrivalRate() {

        return ticketRetrivalRate;
    }

    public void setTicketRetrivalRate(int ticketRetrivalRate) {

        this.ticketRetrivalRate = ticketRetrivalRate;
    }

    public int getCustomerRetrivalRate() {

        return customerRetrivalRate;
    }

    public void setCustomerRetrivalRate(int customerRetrivalRate) {

        this.customerRetrivalRate = customerRetrivalRate;
    }

    // The TicketRepository is injected into the TicketService (To get the TicketRepository bean)
    @Autowired
    public TicketService(TicketRepository ticketRepository) {

        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket) {

        return ticketRepository.save(ticket);
    }
    public synchronized List<Ticket> addTickets(int ticketCount, Event event, Vendor vendor){
        List<Ticket> ticketList=new ArrayList<>();
        if(Thread.currentThread().isInterrupted()){
            System.out.println("The thread "+Thread.currentThread().getName()+" is interrupted");
            notifyAll();
            return null;
        }
        for(int i=0; i<ticketCount; i++){
            try{
                EventService eventService= applicationContext.getBean(EventService.class);
                Ticket ticket=new Ticket(event,vendor);
                createTicket(ticket);
                ticketList.add(ticket);
                event.setEventTicketCount(event.getEventTicketCount()+1);
                eventService.createEvent(event);
                System.out.println("The ticket"+i+"for the thread "+Thread.currentThread().getName()+" has been added");

                try {
                    Thread.currentThread().sleep(ticketRetrivalRate*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                notifyAll();
            }catch(Exception e){
                System.out.println("Error in adding tickets");
                System.out.println(e);
            }

        }
        System.out.println("Tickets added successfully"+" For the thread: "+Thread.currentThread().getName());
        return ticketList;

    }

    public synchronized void buyTicket(Customer customer,Long eventId){
        if(Thread.currentThread().isInterrupted()){
            System.out.println("The thread "+Thread.currentThread().getName()+" is interrupted");
            notifyAll();
            return;
        }
        try{
            Ticket tempTicket=null;
            System.out.println("The name of the thread is: "+Thread.currentThread().getName());
            tempTicket=findFirstTicketWithFalseStatus(eventId);
            while(tempTicket==null){
                System.out.println("No tickets available at buy ticket");
                try {
                    System.out.println("The thread is going to wait");
                    wait();
                    System.out.println("The thread is out of waiting");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
                tempTicket=findFirstTicketWithFalseStatus(eventId);
            }
            tempTicket.setTicketStatus(true);
            tempTicket.setCustomer(customer);
            ticketRepository.save(tempTicket);
            EventService eventService= applicationContext.getBean(EventService.class);
            Event event=eventService.getEventById(eventId);
            event.setEventTicketCount(event.getEventTicketCount()-1);
            eventService.createEvent(event);
            ticketUpdateService.sendAllTicketsForEvent(eventId);
            try {
                Thread.currentThread().sleep(customerRetrivalRate*1000);
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

    public synchronized Ticket findFirstTicketWithFalseStatus(Long eventId) throws Exception {
        List<Ticket> falseticketList = ticketRepository.findByTicketStatusFalseAndEventId(eventId);
        System.out.println("The list is going to be printed in "+Thread.currentThread().getName());
        if(!falseticketList.isEmpty()){
            for (Ticket ticket : falseticketList) {
                System.out.println(ticket);
            }
            return falseticketList.get(0);
        }
        System.out.println("The list is empty");
        return null;
    }

}
