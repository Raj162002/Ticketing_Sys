package cli;

import iit.lk.raj.RajApplication;
import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
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

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(RajApplication.class);
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the total number of tickets per vendor: ");
        int totalTickets = Integer.parseInt(s.next());
        s.nextLine(); //To clear the buffer
        System.out.print("Enter the event name: ");
        String eventName = s.next(); //To clear the buffer
        s.nextLine();
        System.out.print("The required number of vendors :-");
        int vendorCount = Integer.parseInt(s.next());
        System.out.print("Enter the ticket retrival rate:-");
        int ticketRetrivalRate = Integer.parseInt(s.next());
        s.nextLine();
        System.out.print("Enter the customer retrival rate:-");
        int customerRetrivalRate = Integer.parseInt(s.next());
        s.nextLine();
        Event event = new Event(eventName, totalTickets); //Creating an event object
        EventService eventService = context.getBean(EventService.class);
        eventService.createEvent(event);
        VendorService vendorService = context.getBean(VendorService.class);
        TicketService ticketService = context.getBean(TicketService.class);
        ticketService.setTicketRetrivalRate(ticketRetrivalRate);
        ticketService.setCustomerRetrivalRate(customerRetrivalRate);
        CustomerService customerService = context.getBean(CustomerService.class);
        // List to store references to threads
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        for (int i = 0; i < vendorCount; i++) {
            Vendor vendor = new Vendor("Simulator Vendor", "Test@gmail.com", "0771234567", "1234");
            VendorThreaded vendorThreaded = new VendorThreaded(vendor, vendorService, event, totalTickets,ticketService);
            Thread t1 = new Thread(vendorThreaded);
            t1.setName("Vendor Thread " + i);
            vendorThreads.add(t1);  // Add thread to the list
            t1.start();
        }
        Thread.sleep(2000);


        for (int i=0; i<totalTickets*vendorCount; i++) {
            Customer customer = new Customer("Simulator Customer", "TestM@gmail.com", 23L, "1234");
            CustomerThreaded customerThreaded = new CustomerThreaded(ticketService,customer,customerService);
            Thread t2 = new Thread(customerThreaded);
            t2.setName("Customer Thread " + i);
            customerThreads.add(t2);
            t2.start();
        }
        // Join all threads to wait for them to finish
        for (Thread t1 : vendorThreads) {
            t1.join();
            Thread.sleep(1000);// Wait for each thread to finish
        }
        System.out.println("All vendor threads have finished.");
        for(Thread t2: customerThreads){
            t2.join();
            Thread.sleep(1000);// Wait for each thread to finish
        }
        SpringApplication.exit(context);

    }


}
