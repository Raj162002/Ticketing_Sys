package iit.lk.raj.service;

import iit.lk.raj.model.Customer;
import iit.lk.raj.model.Ticket;
import iit.lk.raj.repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private Ticket ticket;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer newCustomer(Customer customer) {
        logger.info("New customer created: " + customer.getCustomerName());
        return customerRepository.save(customer);
    }


    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Customer login(String email, String password) {
        // Look for the customer by email
        Customer customer = customerRepository.findByCustomerEmail(email);

        // Check if customer exists and password matches
        if (customer != null && customer.getCustomerPassword().equals(password)) {
            return customer; // Successful login
        }

        return null; // Invalid credentials
    }
    public synchronized void buyTicket(Customer customer){
        try{
            System.out.println("Customer "+customer.getCustomerName()+" is trying to buy a ticket");
            System.out.println("The name of the thread is: "+Thread.currentThread().getName());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }catch(Exception e){
            System.out.println("Error in buying ticket");
            System.out.println(e);
        }

    }
}
