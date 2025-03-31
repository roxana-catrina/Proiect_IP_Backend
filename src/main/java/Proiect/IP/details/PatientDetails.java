package Proiect.IP.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class PatientDetails implements UserDetails {
    // Manual getters
    @Getter
    private final String id;
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String CNP;
    @Getter
    private final int age;
    @Getter
    private final String address;
    @Getter
    private final String phone;
    private final String email;
    @Getter
    private final String medicalHistory;
    @Getter
    private final String doctorId;
    private final String password;

    public PatientDetails(String id, String firstName, String lastName, String CNP,
                          int age, String address, String phone, String email,
                          String medicalHistory, String doctorId, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.medicalHistory = medicalHistory;
        this.doctorId = doctorId;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_PATIENT"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    // Manual equals/hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientDetails)) return false;
        PatientDetails that = (PatientDetails) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}