package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Customer;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.TicketService;

public class CustomerThreaded extends Customer implements Runnable{
    final private TicketService ticketService;
    private Customer customer;
    final private CustomerService customerService;
    public CustomerThreaded(String customerName, String customerEmail, Long customerContactNumber, String customerPassword, TicketService ticketService, CustomerService customerService) {
        super(customerName, customerEmail, customerContactNumber, customerPassword);
        this.ticketService = ticketService;
        this.customerService = customerService;
    }

    public CustomerThreaded(int customerId, String customerName, String customerEmail, Long customerContactNumber, String customerPassword, TicketService ticketService, Customer customer, CustomerService customerService) {
        super(customerId, customerName, customerEmail, customerContactNumber, customerPassword);
        this.ticketService = ticketService;
        this.customer = customer;
        this.customerService = customerService;
        customerService.createCustomer(customer);
    }

    @Override
    public void run(){
        System.out.println("Customer "+this.getCustomerName()+" is trying to buy a ticket");
        ticketService.buyTicket(customer);
    }
}
