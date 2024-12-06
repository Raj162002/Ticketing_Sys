package iit.lk.raj.controller;

import cli.Config;
import com.google.gson.Gson;
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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {
    List<Thread> vendorThreadeds = new ArrayList<>();
    List<Thread> customerThreadeds = new ArrayList<>();
    private static final String configFilePath = "GUI_simulation_config.json";
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

    private String runSimulation(Config config) throws InterruptedException {
        saveConfigToFile(config);
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
            vendorThreadeds.add(vendorThread);
            vendorThread.start();
        }

        // Simulate customer threads (total tickets * vendor count)
        for (int i = 0; i < config.getTotalTickets() * config.getVendorCount(); i++) {
            Customer customer = new Customer("Simulator Customer " + i, "TestM@gmail.com", 23L, "1234");
            CustomerThreaded customerThreaded = new CustomerThreaded(ticketService, customer, context.getBean(CustomerService.class));
            Thread customerThread = new Thread(customerThreaded);
            customerThread.setName("Customer Thread " + i);
            customerThreadeds.add(customerThread);
            customerThread.start();
        }
        return "All threads have been started successfully!";

        // Wait for threads to finish (join threads)
        // In a real-world scenario, you would handle this more gracefully, possibly with a thread pool
    }

    @PostMapping(value = "/stop")
    public String stopSimulation() {
        for (Thread t1 : vendorThreadeds) {
            if (t1.isAlive()) {
                t1.interrupt();
            }
        }
        for (Thread t2 : customerThreadeds) {
            if (t2.isAlive()) {
                t2.interrupt();
            }
        }
        return "Simulation stopped successfully!";
    }

    private static void saveConfigToFile(Config config) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(configFilePath)) {
            gson.toJson(config, writer);
            System.out.println("Config data saved to " + configFilePath);
        } catch (IOException e) {
            System.out.println("Error saving config data to " + configFilePath);
            e.printStackTrace();
        }
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
