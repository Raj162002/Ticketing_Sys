package iit.lk.raj.controller;

import cli.Config;
import iit.lk.raj.model.*;
import iit.lk.raj.model.multithreaded.*;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.EventService;
import iit.lk.raj.service.TicketService;
import iit.lk.raj.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {
    @Autowired
    private ApplicationContext context;

    @PostMapping("/start")
    public String startSimulation(@RequestBody Config config) {
        try {
            // Trigger simulation logic
            runSimulation(config);

            return "Simulation started successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error starting simulation: " + e.getMessage();
        }
    }

    private void runSimulation(Config config) throws InterruptedException {
        // Simulate the event setup using the provided configuration
        EventService eventService = context.getBean(EventService.class);
        TicketService ticketService = context.getBean(TicketService.class);
        VendorService vendorService = context.getBean(VendorService.class);

        // Setup event and services
        Event event = new Event(config.getEventName(), config.getTotalTickets(), config.getTicketPrice());
        eventService.createEvent(event);
        ticketService.setTicketRetrivalRate(config.getTicketRetrivalRate());
        ticketService.setCustomerRetrivalRate(config.getCustomerRetrivalRate());

        // Create and start vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            Vendor vendor = new Vendor("Simulator Vendor " + i, "Test@gmail.com", 0771234567L, "1234");
            VendorThreaded vendorThreaded = new VendorThreaded(vendor, vendorService, event, config.getTotalTickets(), ticketService);
            Thread vendorThread = new Thread(vendorThreaded);
            vendorThread.setName("Vendor Thread " + i);
            vendorThread.start();
        }

        // Simulate customer threads (total tickets * vendor count)
        for (int i = 0; i < config.getTotalTickets() * config.getVendorCount(); i++) {
            Customer customer = new Customer("Simulator Customer " + i, "TestM@gmail.com", 23L, "1234");
            CustomerThreaded customerThreaded = new CustomerThreaded(ticketService, customer, context.getBean(CustomerService.class));
            Thread customerThread = new Thread(customerThreaded);
            customerThread.setName("Customer Thread " + i);
            customerThread.start();
        }

        // Wait for threads to finish (join threads)
        // In a real-world scenario, you would handle this more gracefully, possibly with a thread pool
    }

//    private final TicketService ticketService;
//    private final CustomerService customerService;
//
//    @Autowired
//    public TicketController(TicketService ticketService, CustomerService customerService) {
//        this.ticketService = ticketService;
//        this.customerService = customerService;
//    }
//
//    @GetMapping(value = "/test")
//    public String test() {
//        return "API works correctly";
//    }
//
//    @PostMapping(value = "/addTicket")
//    public ResponseEntity<Ticket> addTicket(@RequestBody Customer customer){
//        Customer savedCustomer = customerService.createCustomer(customer);
//        Ticket newTicket = new Ticket(customer, TicketType.VIP);
//        Ticket savedTicket = ticketService.createTicket(newTicket);
//        return ResponseEntity.ok(savedTicket);
//    }
//
//    public TicketService getTicketService() {
//        return ticketService;
//    }
//
//    public CustomerService getCustomerService() {
//        return customerService;
//    }
}
