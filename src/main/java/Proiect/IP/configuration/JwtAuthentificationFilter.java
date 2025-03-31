package Proiect.IP.configuration;

import Proiect.IP.repository.DoctorRepository;
import Proiect.IP.repository.PatientRepository;
import Proiect.IP.service.CustomDoctorService;
import Proiect.IP.service.CustomPatientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter  extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthentificationFilter.class);
    private final JwtUtil jwtUtil;
    private final CustomDoctorService doctorService;
    private final CustomPatientService patientService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("Processing request: {}", request.getServletPath());
        if (request.getServletPath().equals("/api/authenticate") ||
                request.getServletPath().equals("/api/doctor-login") ||
                request.getServletPath().equals("/api/patient-login")) {
            log.debug("Skipping authentication endpoints");
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.debug("No valid Bearer token found");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authorizationHeader.substring(7);
        final String userEmail = jwtUtil.extractUserName(jwt);
        log.debug("Extracted email: {}", userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            if (doctorRepository.existsByEmail(userEmail)) {
                log.debug("Loading doctor details for {}", userEmail);
                userDetails = doctorService.loadUserByUsername(userEmail);
            } else if (patientRepository.existsByEmail(userEmail)) {
                log.debug("Loading patient details for {}", userEmail);
                userDetails = patientService.loadUserByUsername(userEmail);
            }
            if (userDetails != null && jwtUtil.isValidToken(jwt, userDetails)) {
                log.debug("Setting authentication for {}", userEmail);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
