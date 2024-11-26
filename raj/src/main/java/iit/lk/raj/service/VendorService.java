package iit.lk.raj.service;

import iit.lk.raj.model.Vendor;
import iit.lk.raj.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Iterable<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    public Vendor login(String email, String password) {
        // Look for the vendor by email
        Vendor vendor = vendorRepository.findByVendorEmail(email);

        // Check if vendor exists and password matches
        if (vendor != null && vendor.getVendorPassword().equals(password)) {
            return vendor; // Successful login
        }

        return null; // Invalid credentials
    }
}
