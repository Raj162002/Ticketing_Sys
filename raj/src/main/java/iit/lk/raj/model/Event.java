package iit.lk.raj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

}
