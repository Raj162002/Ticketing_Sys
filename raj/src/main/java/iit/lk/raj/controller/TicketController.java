package iit.lk.raj.controller;

import cli.Config;
import com.google.gson.Gson;
import iit.lk.raj.model.*;
import iit.lk.raj.model.multithreaded.*;
import iit.lk.raj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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
            String msg = runSimulation(config);
            return "Simulation started successfully!" + msg;
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
            CustomerThreaded customerThreaded = new CustomerThreaded(ticketService, customer, context.getBean(CustomerService.class), event);
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
            t1.interrupt();
        }
        for (Thread t2 : customerThreadeds) {
            t2.interrupt();
        }
        return "Simulation stopping process has started!";
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

    @PostMapping(value = "/addTicket")
    public ResponseEntity<String> addTicket(@RequestBody TicketRequest ticketRequest) {
        TicketService ticketService = context.getBean(TicketService.class);
        Event event= ticketRequest.getEvent();
        event = context.getBean(EventService.class).createEvent(event);
        VendorThreaded vendorThreaded = new VendorThreaded(ticketRequest.getVendor(), context.getBean(VendorService.class), event, ticketRequest.getTicketCount(), ticketService);
        Thread t1 = new Thread(vendorThreaded);
        t1.setName("Vendor Thread " + ticketRequest.getVendor().getVendorName());
        t1.start();
        return ResponseEntity.ok("Ticket creation process started successfully!");
    }

    @PostMapping(value="/buyTicket")
    public ResponseEntity<String> buyTicket(@RequestBody Customer customer, Long eventId){
        TicketService ticketService = context.getBean(TicketService.class);
        EventService eventService = context.getBean(EventService.class);
        Event event = eventService.getEventById(eventId);
        event=eventService.createEvent(event);
        CustomerThreaded customerThreaded = new CustomerThreaded(ticketService, customer, context.getBean(CustomerService.class), event);
        Thread t2 = new Thread(customerThreaded);
        t2.setName("Customer Thread " + customer.getCustomerName());
        return ResponseEntity.ok("Ticket buying process started successfully!");
    }

}