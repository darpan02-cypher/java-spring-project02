package com.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.dto.AuthRequest;
import com.ex.dto.AuthResponse;
import com.ex.util.JwtUtil;

import jakarta.validation.Valid;

/**
 * Authentication Controller
 * Handles login and token validation endpoints
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpiration;

    /**
     * Login endpoint - generates JWT token
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        // In a real application, validate against database
        // For now, we accept any email
        
        if (authRequest.getEmail() == null || authRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse(null, null, "Email is required", 0));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(authRequest.getEmail());
        
        AuthResponse response = new AuthResponse(
            token,
            authRequest.getEmail(),
            "Login successful",
            jwtExpiration
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Validate token endpoint
     * GET /api/auth/validate
     * Header: Authorization: Bearer <token>
     */
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid token format. Use: Authorization: Bearer <token>");
        }

        String token = authHeader.substring(7);  // Remove "Bearer " prefix
        
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
            }
            return ResponseEntity.ok("Token is valid. Email: " + email);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
