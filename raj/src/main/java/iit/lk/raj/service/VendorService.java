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
}
