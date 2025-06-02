package Proiect.IP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Entity
@Data
@Document(collection = "sensors")
public class Sensor {
    @Id
    @Field("_id")
    private String id;
    @Field("patient_id")
    private String  patientId;
    @Field("ekg_signal")
    private String ekgSignal;
    @Field("heart_rate")
    private String heartRate;
    private double temperature;
    private double humidity;
    private LocalDateTime timestamp;
}
