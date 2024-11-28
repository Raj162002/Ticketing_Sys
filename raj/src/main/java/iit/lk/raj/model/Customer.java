package iit.lk.raj.model;

import iit.lk.raj.repository.TicketRepository;
import iit.lk.raj.service.TicketService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Runnable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String customerName;
    private String customerEmail;
    private Long customerContactNumber;
    private String customerPassword;
    private TicketRepository ticketRepository;

    // One customer can have multiple tickets
//    @OneToMany(mappedBy = "customer")
//    private List<Ticket> tickets;

    public Customer(String customerName, String customerEmail, Long customerContactNumber, String customerPassword) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerContactNumber = customerContactNumber;
        this.customerPassword = customerPassword;
    }
    //The Constructor that is going to be used for the simulation
    public Customer(String customerName){
        this.customerName = customerName;
    }
    @Override
    public void run(){
        System.out.println("Customer thread is running "+"The thread name is "+Thread.currentThread().getName());

    }
}
