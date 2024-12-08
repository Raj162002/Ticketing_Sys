package iit.lk.raj.controller.websocket;

public class TicketUpdate {

    private Long ticketId;
    private Long eventId;
    private Long vendorId;
    private int customerId;  // Can be null if not assigned
    private boolean status;

    public TicketUpdate(Long ticketId, Long eventId, Long vendorId, int customerId, boolean status) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.vendorId = vendorId;
        this.customerId = customerId;
        this.status = status;
    }
}
