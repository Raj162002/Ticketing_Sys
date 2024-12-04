package iit.lk.raj.model;

import jakarta.persistence.*;
import lombok.*;
import iit.lk.raj.model.Vendor;


@Entity
@Data// Lombok will generate getters, setters, toString
@NoArgsConstructor  // Lombok generates a no-args constructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Enumerated(EnumType.STRING) // Enum to store ticket types (VIP, Normal, etc.)
    private TicketType ticketType;

    private boolean ticketStatus;

    private double ticketPrice;


    public Ticket(Event event, Vendor vendor) {
        this.ticketType = TicketType.NORMAL;
        this.ticketStatus = false;
        this.event = event;
        this.vendor = vendor;
        this.ticketPrice = event.getEventTicketPrice();
    }

    @ManyToOne
    @JoinColumn(name = "eventId")  // Foreign key to Event
    private Event event;
    @ManyToOne
    @JoinColumn(name = "vendorId", nullable = true)  // vendor_id will be nullable
    private Vendor vendor;
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)  // vendor_id will be nullable
    private Customer customer;


    // Constructor with ticket type and price
//    public Ticket(String ticketType, Customer customer) {
//        this.ticketType = TicketType.valueOf(ticketType.toUpperCase());
//        this.customer = customer;
//    }
//    public Ticket(String ticketType, Customer customer,int ticketCount) {
//        if (!ticketStatus) {
//            throw new TicketNotAvailable(ticketId);
//        } else {
//            this.ticketType = TicketType.valueOf(ticketType.toUpperCase());
//
//        }
//    }




}
