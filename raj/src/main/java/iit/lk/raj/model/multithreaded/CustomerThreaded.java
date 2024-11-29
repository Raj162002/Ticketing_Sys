package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Customer;
import iit.lk.raj.service.TicketService;

public class CustomerThreaded extends Customer implements Runnable{
    final private TicketService ticketService;
    public CustomerThreaded(String customerName, String customerEmail, Long customerContactNumber, String customerPassword, TicketService ticketService) {
        super(customerName, customerEmail, customerContactNumber, customerPassword);
        this.ticketService = ticketService;
    }
    public void run(){
        System.out.println("Customer "+this.getCustomerName()+" is trying to buy a ticket");
        System.out.println("The name of the thread is: "+Thread.currentThread().getName());
    }
}
