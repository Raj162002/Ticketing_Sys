package iit.lk.raj.controller;

import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.model.TicketType;
import iit.lk.raj.service.CustomerService;
import iit.lk.raj.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController {
    private final TicketService ticketService;
    private final CustomerService customerService;

    @Autowired
    public TicketController(TicketService ticketService, CustomerService customerService) {
        this.ticketService = ticketService;
        this.customerService = customerService;
    }

    @GetMapping("/test")
    public String test() {
        return "API works correctly";
    }

    @PostMapping("/addTicket")
    public ResponseEntity<Ticket> addTicket(@RequestBody Customer customer){
        Customer savedCustomer = customerService.createCustomer(customer);
        Ticket newTicket = new Ticket(customer, TicketType.VIP);
        Ticket savedTicket = ticketService.createTicket(newTicket);
        return ResponseEntity.ok(savedTicket);
    }



}
