package iit.lk.raj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Vendor {
    private @Id @GeneratedValue Long vendorId;
    private String vendorName;
    private String vendorEmail;
    private String vendorContactNumber;
}
