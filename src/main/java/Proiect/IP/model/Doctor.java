package Proiect.IP.model;



import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NonNull;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@Document(collection = "doctors")
public class Doctor {
    @Id
    @Field("_id")
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
   // private String specialization="medical";
}
