package iit.lk.raj.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String message) {

      super(message);
    }
  // This constructor is used to throw an exception when a ticket is not found by its ID
    public TicketNotFoundException(Long id) {
      super("Ticket not found with ID: " + id);
    }
}
