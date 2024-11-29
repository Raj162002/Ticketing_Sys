package cli;

import iit.lk.raj.RajApplication;
import iit.lk.raj.controller.TicketController;
import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Event;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.model.multithreaded.VendorThreaded;
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
        String eventName=s.next(); //To clear the buffer
        s.nextLine();
        Event event=new Event(eventName,totalTickets); //Creating an event object
        EventService eventService = context.getBean(EventService.class);
        Event tempEvent=eventService.createEvent(event);
        VendorService vendorService = context.getBean(VendorService.class);
        // List to store references to threads
        List<Thread> vendorThreads = new ArrayList<>();

        for(int i=0;i<10;i++){
            VendorThreaded vendorThreaded = new VendorThreaded("Simulator Vendor","Test@gmail.com","0771234567","1234",vendorService,event,totalTickets);
            Thread t = new Thread(vendorThreaded);
            t.setName("Vendor Thread "+i);
            vendorThreads.add(t);  // Add thread to the list
            t.start();
        }
        // Join all threads to wait for them to finish
        for (Thread t : vendorThreads) {
            t.join();  // Wait for each thread to finish
        }
        System.out.println("All vendor threads have finished.");




    }


}
