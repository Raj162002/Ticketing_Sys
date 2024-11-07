package iit.lk.raj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Ticket {
    @Id
    private int ticketId;
    private String ticketType;
    private double ticketPrice;
    private Date ticketReleaseDate;
    private Date ticketClosingDate;
    private int ticketTotal;
    private boolean ticketStatus;


    public int getTicketTotal() {
        return ticketTotal;
    }

    public void setTicketTotal(int ticketTotal) {
        this.ticketTotal = ticketTotal;
    }

    public Date getTicketReleaseDate() {
        return ticketReleaseDate;
    }

    public void setTicketReleaseDate(Date ticketReleaseDate) {
        this.ticketReleaseDate = ticketReleaseDate;
    }


    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public boolean isTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(boolean ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Ticket(int ticketId, String ticketType, double ticketPrice) {
        if(!ticketStatus){
            System.out.println("Ticket is not available");
        }else {
            this.ticketId = ticketId;
            this.ticketType = ticketType;
            this.ticketPrice = ticketPrice;
        }
    }
}
