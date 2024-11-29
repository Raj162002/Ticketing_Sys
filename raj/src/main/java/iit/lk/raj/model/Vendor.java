package iit.lk.raj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Vendor {
    private @Id @GeneratedValue Long vendorId;
    private String vendorPassword;
    private String vendorName;
    private String vendorEmail;
    private String vendorContactNumber;
    protected Vendor(String vendorName, String vendorEmail, String vendorContactNumber, String vendorPassword){
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
        this.vendorContactNumber = vendorContactNumber;
        this.vendorPassword = vendorPassword;
    }
    protected Vendor(){

    }


    protected Vendor(String vendorName){
        this.vendorName = vendorName;
    }
}
