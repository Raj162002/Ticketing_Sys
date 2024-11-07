package iit.lk.raj.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data  // Lombok will generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok generates a no-args constructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @Enumerated(EnumType.STRING) // Enum to store ticket types (VIP, Normal, etc.)
    private TicketType ticketType;

    private double ticketPrice;

    private Date ticketReleaseDate;

    private Date ticketClosingDate;

    private int ticketTotal;

    private boolean ticketStatus;

    // Many-to-one relationship with Customer (a customer can have many tickets)
    @ManyToOne
    @JoinColumn(name = "customer_id")  // Foreign key to Customer
    private Customer customer;

    // Constructor with ticket type and price
    public Ticket(String ticketType, double ticketPrice) {
        if (!ticketStatus) {
            System.out.println("Ticket is not available");
        } else {
            this.ticketType = TicketType.valueOf(ticketType.toUpperCase()); // Convert String to Enum
            this.ticketPrice = ticketPrice;
        }
    }

    // Constructor for full initialization
    public Ticket(int ticketId, TicketType ticketType, double ticketPrice, int ticketTotal, boolean ticketStatus) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.ticketPrice = ticketPrice;
        this.ticketTotal = ticketTotal;
        this.ticketStatus = ticketStatus;
    }
}
