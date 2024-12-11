package iit.lk.raj.controller;

import iit.lk.raj.model.Vendor;
import iit.lk.raj.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 This is the vendor controller which accepts the api calls
 and does the works using the vendor service
*/
@RestController
@RequestMapping(value = "/vendor")
@CrossOrigin(origins = "*")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    //This request is used to create a vendor by sending a vendor object(JSON body)
    @PostMapping(value = "/create")
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }

    //This request was used by postman to test and get all the vendors from database to diagnose
    @GetMapping(value="/getAllVendors")
    public Iterable<Vendor> getAllVendors(){
        return vendorService.getAllVendors();
    }

    //This request is used to login a vendor by sending a vendor object(JSON body)
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Vendor loginRequest) {
        String email = loginRequest.getVendorEmail();
        String password = loginRequest.getVendorPassword();
        Vendor vendor = vendorService.login(email, password);

        if(vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

    }
}
