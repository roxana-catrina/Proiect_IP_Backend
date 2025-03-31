package Proiect.IP.Authentication;

import lombok.Data;

@Data
public class AuthenticationRequest {
  private String email;
  private String password;
}
