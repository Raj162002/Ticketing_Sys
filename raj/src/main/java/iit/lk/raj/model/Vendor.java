package iit.lk.raj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    private String vendorPassword;
    private String vendorName;
    private String vendorEmail;
    private String vendorContactNumber;
    public Vendor(String vendorName, String vendorEmail, String vendorContactNumber, String vendorPassword){
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
        this.vendorContactNumber = vendorContactNumber;
        this.vendorPassword = vendorPassword;
    }
    public Vendor(){

    }


    public Vendor(String vendorName){
        this.vendorName = vendorName;
    }

}
