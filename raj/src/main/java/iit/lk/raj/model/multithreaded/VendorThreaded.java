package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Event;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.service.TicketService;
import iit.lk.raj.service.VendorService;

import java.util.logging.Logger;

/*
This is the Vendor entity thread class
The reason this was made because when the customer class is extended to thread or implements thread
the run method must be overridden but if it was done in a database class it gave a error
for the new threaded class was made extends the entity class and implemented runnable
 */

public class VendorThreaded extends Vendor implements Runnable {
    private Event event;
    private int ticketCount;
    private Vendor vendor;
    final private VendorService vendorService;
    final private TicketService ticketService;
    Logger logger = Logger.getLogger(VendorThreaded.class.getName());


    public VendorThreaded(String vendorName, VendorService vendorService, TicketService ticketService) {
        super(vendorName);
        this.vendorService = vendorService;
        this.ticketService = ticketService;
    }


    public VendorThreaded(String vendorName, String vendorEmail, Long vendorContactNumber, String vendorPassword, VendorService vendorService, Event event, int ticketCount, TicketService ticketService) {
        super(vendorName, vendorEmail, vendorContactNumber, vendorPassword);
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
        this.ticketService = ticketService;
    }

    //Using this for multi thread simulation
    public VendorThreaded(Vendor vendor, VendorService vendorService, Event event, int ticketCount, TicketService ticketService) {
        super(vendor.getVendorName(), vendor.getVendorEmail(), vendor.getVendorContactNumber(), vendor.getVendorPassword());
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
        this.vendor=vendor;
        this.ticketService = ticketService;
        vendorService.createVendor(vendor);
    }

    public VendorThreaded(VendorService vendorService, Event event, int ticketCount, TicketService ticketService) {
        super();
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
        this.ticketService = ticketService;
    }

    /*
    This is the method that invokes when thread.start() is called
    it uses ticketService which acts as a ticket pool as mentioned in customer threaded class
     */
    @Override
    public void run() {
            try{
                logger.info("The name of the thread is: " + Thread.currentThread().getName());
                ticketService.addTickets(ticketCount, event,vendor);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        System.out.println(Thread.currentThread().getName()+" has stopped");


    }
}
