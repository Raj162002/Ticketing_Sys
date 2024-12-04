package iit.lk.raj.model.multithreaded;

import iit.lk.raj.model.Event;
import iit.lk.raj.model.Vendor;
import iit.lk.raj.service.TicketService;
import iit.lk.raj.service.VendorService;

public class VendorThreaded extends Vendor implements Runnable {
    private Event event;
    private int ticketCount;
    private Vendor vendor;
    final private VendorService vendorService;
    final private TicketService ticketService;
    public VendorThreaded(String vendorName, VendorService vendorService, TicketService ticketService) {
        super(vendorName);
        this.vendorService = vendorService;
        this.ticketService = ticketService;
    }
    public VendorThreaded(String vendorName, String vendorEmail, String vendorContactNumber, String vendorPassword, VendorService vendorService, Event event, int ticketCount, TicketService ticketService) {
        super(vendorName, vendorEmail, vendorContactNumber, vendorPassword);
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
        this.ticketService = ticketService;
    }
    public VendorThreaded(Vendor vendor, VendorService vendorService, Event event, int ticketCount, TicketService ticketService) {
//        super(vendor.getVendorName(), vendor.getVendorEmail(), vendor.getVendorContactNumber(), vendor.getVendorPassword());
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
        this.vendor=vendor;
        this.ticketService = ticketService;
        vendorService.createVendor(vendor);
    }

    //Using this for simulation
    public VendorThreaded(VendorService vendorService, Event event, int ticketCount, TicketService ticketService) {
        super();
        this.vendorService = vendorService;
        this.event=event;
        this.ticketCount=ticketCount;
        this.ticketService = ticketService;
    }
    @Override
    public void run() {
//        System.out.println("Vendor " + this.getVendorName() + " is trying to add an event");
        System.out.println("The name of the thread is: " + Thread.currentThread().getName());
        ticketService.addTickets(ticketCount, event,vendor);

    }
}
