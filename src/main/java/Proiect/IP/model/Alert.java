package Proiect.IP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Entity
@Data
@Document(collection = "alerts")
public class Alert {
    @Id
    @Field("_id")
    private String id;
    @Field("patient_id")
    private String patientId;
    private String message;
    private LocalDateTime timestamp;

}
