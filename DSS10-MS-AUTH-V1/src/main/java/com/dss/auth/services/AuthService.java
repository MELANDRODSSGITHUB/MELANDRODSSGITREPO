package com.dss.auth.services;

import com.dss.auth.entities.User;
import com.dss.auth.exception.LoginAuthenticationException;
import com.dss.auth.model.AuthRequest;
import com.dss.auth.model.AuthResponse;
import com.dss.auth.model.LoginRequest;
import com.dss.auth.repository.AdminRepository;
import com.dss.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.dss.auth.util.ResponseMsgConstant.*;

@Service
public class AuthService {

    @Autowired
    AdminRepository adminRepository;

    private final RestTemplate restTemplate;
    private final JwtUtil jwt;

    @Autowired
    public AuthService(RestTemplate restTemplate,
                       final JwtUtil jwt) {
        this.restTemplate = restTemplate;
        this.jwt = jwt;
    }

    public ResponseEntity<AuthResponse> register(AuthRequest authRequest) {
        //do validation if user already exists
        authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));

        ResponseEntity<String> response = restTemplate.postForObject("http://admin-service/dss/api/admin/register", authRequest, ResponseEntity.class);

        if (!HttpStatus.CREATED.equals(response.getStatusCode())) {
            return ResponseEntity.status(response.getStatusCode())
                    .body(new AuthResponse("", "", response.getBody()));
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", authRequest.getEmail());
        claims.put("roles", authRequest.getRole());
        String accessToken = jwt.doGenerateToken(claims, authRequest.getEmail(), "ACCESS");
        String refreshToken = jwt.doGenerateToken(claims, authRequest.getEmail(), "REFRESH");
        return ResponseEntity.status(response.getStatusCode())
                .body(new AuthResponse(accessToken, refreshToken, response.getBody()));
    }

    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        Optional<User> optionalAdmin = adminRepository.findAdminByEmail(loginRequest.getEmail());

        if (!optionalAdmin.isPresent() || !BCrypt.checkpw(loginRequest.getPassword(), optionalAdmin.get().getPassword())) {
            throw new LoginAuthenticationException(LOGIN_AUTHENTICATION_FAILED_MSG);
        }
        User user = optionalAdmin.get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getUserId());
        claims.put("roles", user.getRoles());

        String accessToken = jwt.doGenerateToken(claims, user.getEmail(), "ACCESS");
        String refreshToken = jwt.doGenerateToken(claims, user.getEmail(), "REFRESH");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthResponse(accessToken, refreshToken, LOGIN_AUTHENTICATION_SUCCESS_MSG));
    }
}
