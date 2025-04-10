package Proiect.IP.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String cnp;
    private String phone;
    private String address;
    private String medicalHistory;
    private String password;
    private int age;
}