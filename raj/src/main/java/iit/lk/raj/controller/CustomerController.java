package iit.lk.raj.controller;

import iit.lk.raj.model.Customer;
import iit.lk.raj.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/create")
    public Customer createCustomer(@RequestBody Customer customer)
    {
        return customerService.createCustomer(customer);
    }
    @GetMapping(value="/getAllCustomers")
    public Iterable<Customer> getAllCustomers(){

        return customerService.getAllCustomers();
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Customer loginRequest) {
        // Assuming your login request contains email and password
        String email = loginRequest.getCustomerEmail();
        String password = loginRequest.getCustomerPassword();

        // Call the service to authenticate the user
        Customer customer = customerService.login(email, password);

        if (customer != null) {
            // If customer is found, return the customer object
            return ResponseEntity.ok(customer);
        } else {
            // If customer is not found or password is incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}
