package iit.lk.raj.exception;

//A custom Exception used when ticket is not found used in TicketService
public class TicketNotFoundException extends RuntimeException {
  // This constructor is used to throw an exception when a ticket is not found by its ID
    public TicketNotFoundException(Long id) {
      super("Ticket not found with ID: " + id);
    }
}
