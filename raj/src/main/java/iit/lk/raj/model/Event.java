package iit.lk.raj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String eventName;
    private String eventLocation;
    private Date eventDate;
    private double eventTicketPrice;
    private long eventTotalTicketsVendor;
    public Event(String eventName, String eventLocation, Date eventDate, double eventTicketPrice,  long eventTotalTickets) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTicketPrice = eventTicketPrice;
        this.eventTotalTicketsVendor = eventTotalTickets;
    }
    //The Constructor that is going to be used for the simulation
    public Event (String eventName,long eventTotalTickets,double eventTicketPrice){
        this.eventName = eventName;
        this.eventTotalTicketsVendor = eventTotalTickets;
        this.eventTicketPrice = eventTicketPrice;

    }

}
