package iit.lk.raj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String eventName;
    private String eventLocation;
    private Date eventDate;
    private String eventStartTime;
    private String eventEndTime;
    private long eventTicketCount;
    private long eventTicketPrice;
    private long eventVIPPrice;
    private Date eventTicketReleaseDate;
    private Date eventTicketClosingDate;
    private boolean eventStatus;
    private long eventTotalTickets;
    public Event(String eventName, String eventLocation, Date eventDate, String eventStartTime, String eventEndTime, long eventTicketCount, long eventTicketPrice, long eventVIPPrice, Date eventTicketReleaseDate, Date eventTicketClosingDate, long eventTotalTickets) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventTicketCount = eventTicketCount;
        this.eventTicketPrice = eventTicketPrice;
        this.eventVIPPrice = eventVIPPrice;
        this.eventTicketReleaseDate = eventTicketReleaseDate;
        this.eventTicketClosingDate = eventTicketClosingDate;
        this.eventTotalTickets = eventTotalTickets;
    }
    //The Constructor that is going to be used for the simulation
    public Event (String eventName,long eventTotalTickets,long eventTicketPrice){
        this.eventName = eventName;
        this.eventTotalTickets = eventTotalTickets;
        this.eventTicketPrice = eventTicketPrice;
    }
    @ManyToOne
    @JoinColumn(name = "vendorId")
    private Vendor vendor;

}
