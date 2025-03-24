package Proiect.IP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "recommendations")
public class Recommendation {

    @Id
    @Field("_id")
    private String id; // Câmpul _id este obligatoriu în MongoDB

    @Field("patient_id")
    private String patientId;

    @Field("doctor_id")
    private String  doctorId;

    @Field("activity_type")
    private String activityType;

    @Field("duration")
    private String duration;

    @Field("created_at")
    private LocalDateTime createdAt;

}
