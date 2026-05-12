package com.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ex.dto.AuthResponse;
import com.ex.util.JwtUtil;

/**
 * Authentication Controller
 * Handles login and token validation endpoints
 * No email or password validation required - simple token generation
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Simple login endpoint - generates JWT token without validation
     * No email or password required
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login() {
        // Generate token with a default email
        String token = jwtUtil.generateToken("user@student.com");
        
        AuthResponse response = new AuthResponse(
            token,
            "user@student.com",
            "Token generated successfully",
            3600000
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Generate token with custom email (optional parameter)
     * POST /api/auth/login-with-email?email=john@example.com
     */
    @PostMapping("/login-with-email")
    public ResponseEntity<AuthResponse> loginWithEmail(
            @RequestParam(required = false, defaultValue = "user@student.com") String email) {
        
        String token = jwtUtil.generateToken(email);
        
        AuthResponse response = new AuthResponse(
            token,
            email,
            "Token generated successfully",
            3600000
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Validate token endpoint
     * GET /api/auth/validate
     * Header: Authorization: Bearer <token>
     */
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                .body("Invalid token format. Use: Authorization: Bearer <token>");
        }

        String token = authHeader.substring(7);  // Remove "Bearer " prefix
        
        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.extractEmail(token);
            if (jwtUtil.isTokenExpired(token)) {
                return ResponseEntity.status(401).body("Token expired");
            }
            return ResponseEntity.ok("Token is valid. Email: " + email);
        }
        
        return ResponseEntity.status(401).body("Invalid token");
    }
}
