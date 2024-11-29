package iit.lk.raj.service;

import iit.lk.raj.model.Event;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;
    private final TicketService ticketService;

    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketService ticketService) {
        this.vendorRepository = vendorRepository;
        this.ticketService = ticketService;
    }
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Iterable<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    public Vendor login(String email, String password) {
        // Look for the vendor by email
        Vendor vendor = vendorRepository.findByVendorEmail(email);

        // Check if vendor exists and password matches
        if (vendor != null && vendor.getVendorPassword().equals(password)) {
            return vendor; // Successful login
        }

        return null; // Invalid credentials
    }
    public synchronized void addTickets(int ticketCount, Event event,Vendor vendor){
        for(int i=0; i<ticketCount; i++){
           try{
               Ticket ticket=new Ticket(event,vendor);
               Ticket tempTicket=ticketService.createTicket(ticket);
               System.out.println("The ticket"+i+"for the thread "+Thread.currentThread().getName()+" has been added");
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }catch(Exception e){
               System.out.println("Error in adding tickets");
               System.out.println(e);
           }

        }
        System.out.println("Tickets added successfully"+" For the thread: "+Thread.currentThread().getName());

    }
}
