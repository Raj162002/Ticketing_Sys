package iit.lk.raj.controller;

import iit.lk.raj.model.Vendor;
import iit.lk.raj.service.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vendor")
@CrossOrigin(origins = "*")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping(value = "/create")
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorService.createVendor(vendor);
    }
    @GetMapping(value="/getAllVendors")
    public Iterable<Vendor> getAllVendors(){
        return vendorService.getAllVendors();
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Vendor loginRequest) {
        // Assuming your login request contains email and password
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
