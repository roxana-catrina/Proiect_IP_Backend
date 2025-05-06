package Proiect.IP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Entity
@Document(collection = "patients")
public class Patient {
    @Id
    @Field("_id")
    private String id;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    private String CNP;  //cand introduci din postman cnp trb sa fie cu litere mici alfetl e nulll
    private int age;
    private String address;
    private String phone;
    private String email;
    @Field("medical_history")
    private String medicalHistory;
    @Field("doctor_id")
    private String doctorId;
    private String password;
    @Field("dateBirth")
    private Date dateBirth;
    private String job;

}
