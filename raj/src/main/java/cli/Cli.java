package cli;

import iit.lk.raj.RajApplication;
import iit.lk.raj.controller.TicketController;
import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.model.multithreaded.CustomerThreaded;
import iit.lk.raj.model.multithreaded.VendorThreaded;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.EventService;
import iit.lk.raj.service.TicketService;
import iit.lk.raj.service.VendorService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cli {
    private int totalTickets;

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(RajApplication.class);
        TicketController ticketController = context.getBean(TicketController.class);

        Scanner s = new Scanner(System.in);
        System.out.print("Enter the total number of tickets: ");
        int totalTickets = Integer.parseInt(s.next());
        s.nextLine(); //To clear the buffer
        System.out.print("Enter the event name: ");
        String eventName = s.next(); //To clear the buffer
        s.nextLine();
        Event event = new Event(eventName, totalTickets); //Creating an event object
        EventService eventService = context.getBean(EventService.class);
        Event tempEvent = eventService.createEvent(event);
        Event event2 = new Event(eventName, totalTickets); //Creating an event object
        EventService eventService2 = context.getBean(EventService.class);
        Event tempEvent2 = eventService.createEvent(event2);
        VendorService vendorService = context.getBean(VendorService.class);
        TicketService ticketService = context.getBean(TicketService.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        // List to store references to threads
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Vendor vendor = new Vendor("Simulator Vendor", "Test@gmail.com", "0771234567", "1234");
            VendorThreaded vendorThreaded = new VendorThreaded(vendor, vendorService, tempEvent2, totalTickets);
            Thread t = new Thread(vendorThreaded);
            t.setName("Vendor Thread " + i);
            vendorThreads.add(t);  // Add thread to the list
            t.start();
        }
        // Join all threads to wait for them to finish
        for (Thread t : vendorThreads) {
            t.join();  // Wait for each thread to finish
        }
        System.out.println("All vendor threads have finished.");

        for (int i=0; i<10; i++) {
            Customer customer = new Customer("Simulator Customer", "TestM@gmail.com", 23L, "1234");
            CustomerThreaded customerThreaded = new CustomerThreaded(ticketService,customer,customerService);
            Thread t2 = new Thread(customerThreaded);
            t2.setName("Customer Thread " + i);
            customerThreads.add(t2);
            t2.start();
        }
        for(Thread t2: customerThreads){
            t2.join();
        }

    }


}
