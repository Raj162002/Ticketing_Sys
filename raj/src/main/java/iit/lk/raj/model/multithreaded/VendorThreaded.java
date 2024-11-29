package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Event;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.service.VendorService;

public class VendorThreaded extends Vendor implements Runnable {
    private Event event;
    private int ticketCount;
    final private VendorService vendorService;
    public VendorThreaded(String vendorName, VendorService vendorService) {
        super(vendorName);
        this.vendorService = vendorService;
    }
    public VendorThreaded(String vendorName, String vendorEmail, String vendorContactNumber, String vendorPassword, VendorService vendorService,Event event,int ticketCount) {
        super(vendorName, vendorEmail, vendorContactNumber, vendorPassword);
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
    }

    //Using this for simulation
    public VendorThreaded(VendorService vendorService,Event event,int ticketCount) {
        super();
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;

    }

    public void run() {
//        System.out.println("Vendor " + this.getVendorName() + " is trying to add an event");
        System.out.println("The name of the thread is: " + Thread.currentThread().getName());
        vendorService.addTickets(ticketCount, event);

    }
}
