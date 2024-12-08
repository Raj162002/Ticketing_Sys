package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.TicketService;

public class CustomerThreaded extends Customer implements Runnable{
    final private TicketService ticketService;
    private Customer customer;
    final private CustomerService customerService;
    private boolean threadRunning = true;
    private Event event;
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

    @Override
    public void run(){
        while (threadRunning){
            try {
                System.out.println("Customer "+this.getCustomerName()+" is trying to buy a ticket");
                ticketService.buyTicket(customer,event.getEventId());
                break;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(Thread.currentThread().getName()+" has stopped");

    }
}
