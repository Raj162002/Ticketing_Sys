package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.TicketService;
import java.util.logging.Logger;

/*
This is the Customer entity thread class
The reason this was made because when the customer class is extended to thread or implements thread
the run method must be overridden but if it was done in a database class it gave a error
for the new threaded class was made extends the entity class and implemented runnable
 */
public class CustomerThreaded extends Customer implements Runnable{
    final private TicketService ticketService;
    private Customer customer;
    final private CustomerService customerService;
    private boolean threadRunning = true;
    private Event event;
    Logger logger = Logger.getLogger(CustomerThreaded.class.getName());

    public CustomerThreaded(String customerName, String customerEmail, Long customerContactNumber, String customerPassword, TicketService ticketService, CustomerService customerService) {
        super(customerName, customerEmail, customerContactNumber, customerPassword);
        this.ticketService = ticketService;
        this.customerService = customerService;
    }

    public CustomerThreaded( TicketService ticketService, Customer customer, CustomerService customerService,Event event) {
        super(customer.getCustomerName(), customer.getCustomerEmail(), customer.getCustomerContactNumber(), customer.getCustomerPassword());
        this.ticketService = ticketService;
        this.customer = customer;
        this.customerService = customerService;
        this.event = event;
        this.customerService.createCustomer(customer);
    }

    //Even though LomBok was imported using that was causing some errors due to that it was not used
    public boolean isThreadRunning() {
        return threadRunning;
    }

    public void setThreadRunning(boolean threadRunning) {
        this.threadRunning = threadRunning;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    /*
    This is the method called when the thread.start() is called
    it uses the ticket service class which acts as a ticketpool and buyTicket() is synchronized
     */
    @Override
    public void run(){
            try {
                logger.info("Customer "+this.getCustomerName()+" is trying to buy a ticket");
                ticketService.buyTicket(customer,event.getEventId());
            }catch (Exception e) {
                throw new RuntimeException(e);
            }

    }
}
