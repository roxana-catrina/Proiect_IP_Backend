package Proiect.IP.Authentication;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private String email;
}
