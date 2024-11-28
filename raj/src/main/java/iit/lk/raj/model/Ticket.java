package iit.lk.raj.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data// Lombok will generate getters, setters, toString
@NoArgsConstructor  // Lombok generates a no-args constructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;

    @Enumerated(EnumType.STRING) // Enum to store ticket types (VIP, Normal, etc.)
    private TicketType ticketType;

    private boolean ticketStatus;


    // Many-to-one relationship with Customer (a customer can have many tickets)
    @ManyToOne
    @JoinColumn(name = "customerId")  // Foreign key to Customer
    private Customer customer;

    public Ticket (Customer customer, TicketType ticketType) {
        this.ticketType = ticketType;
        this.customer = customer;
        this.ticketStatus = true;
    }
    public Ticket (Customer customer,Event event){
        this.customer = customer;
        this.ticketStatus = true;
        this.ticketType = TicketType.NORMAL;
        this.event = event;
    }

    @ManyToOne
    @JoinColumn(name = "eventId")  // Foreign key to Event
    private Event event;

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
