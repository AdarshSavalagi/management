package com.sitmng.management.controller;

import com.sitmng.management.dto.LoginDTO;
import com.sitmng.management.dto.SignupDTO;
import com.sitmng.management.dto.TokenVerify;
import com.sitmng.management.models.Admin;
import com.sitmng.management.security.JwtUtil;
import com.sitmng.management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;


   @Autowired
    public AdminController(AdminService adminService, JwtUtil jwtUtil ) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody LoginDTO loginDTO) {
        // Fetch the user by username
        Admin admin = adminService.getUserByUsername(loginDTO.getUsername());

        Map<String, String> response = new HashMap<>();
        if (admin == null) {
            // Return 404 Not Found if user does not exist
            response.put("status", "error");
            response.put("message", "Invalid username or password.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Verify the password
        if (adminService.verifyAdminPassword(admin, loginDTO.getPassword())) {
            String token =  jwtUtil.generateToken(admin.getUsername());
            admin.setToken(token);
            LocalDateTime now = LocalDateTime.now().plusHours(1);
            admin.setTokenExpiry(now);
            adminService.save(admin);
            response.put("status", "success");
            response.put("token",token);
            response.put("message", "Login successful.");

            return ResponseEntity.ok(response);
        }

        // Return 401 Unauthorized if password is incorrect
        response.put("status", "error");
        response.put("message", "Invalid username or password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> adminCreate(@RequestBody SignupDTO signupDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            adminService.createAdmin(signupDTO.getUsername(), signupDTO.getPassword(), signupDTO.getName());
            response.put("status", "success");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String,String>> adminVerify(@RequestBody TokenVerify token) {
        Map<String, String> response = new HashMap<>();
        response.put("message", jwtUtil.validateToken(token.getToken()).toString());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
