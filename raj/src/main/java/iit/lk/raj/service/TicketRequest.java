package iit.lk.raj.service;

import iit.lk.raj.model.Event;
import iit.lk.raj.model.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketRequest {
    private Vendor vendor;
    private Event event;
    private int ticketCount;
}
