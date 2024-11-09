package iit.lk.raj.exception;

public class TicketNotAvailable extends RuntimeException {
    public TicketNotAvailable(String message) {

      super(message);
    }
    public TicketNotAvailable(long id) {
      super("Ticket not available with ID: " + id);
    }
}
