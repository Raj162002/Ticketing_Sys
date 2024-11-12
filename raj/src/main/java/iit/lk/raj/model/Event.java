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
    private @Id @GeneratedValue long eventId;
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
    @ManyToOne
    @JoinColumn(name = "vendorId")
    private Vendor vendor;

}
