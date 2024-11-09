package iit.lk.raj.service;

import iit.lk.raj.model.Customer;
import iit.lk.raj.repository.CustomerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger logger = Logger.getLogger(TicketService.class.getName());

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }



}
