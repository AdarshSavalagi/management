package com.sitmng.management.service;


import com.sitmng.management.models.Admin;
import com.sitmng.management.respository.AdminRepo;
import com.sitmng.management.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AdminService {


    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder ;
    private final JwtUtil jwtUtil ;

    @Autowired
    public AdminService(AdminRepo adminRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminRepo = adminRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin getUserByUsername(String username){
        return adminRepo.findByUsername(username);
    }

    public void save(Admin admin){
        adminRepo.save(admin);
    }

    public void createAdmin(String username, String password, String name) throws Exception {
        Admin test = adminRepo.findByUsername(username);
        if(test != null){
            throw new Exception("Admin Username Exists");
        }
        Admin admin = new Admin(name, username);
        String hashPassword =passwordEncoder.encode(password);
        admin.setPassword_hash(hashPassword);
        adminRepo.save(admin);
    }

    public boolean verifyAdminPassword(Admin admin, String password){
        return passwordEncoder.matches(password, admin.getPassword_hash());
    }

    public boolean verifyAdminByToken(String token){
        if(!jwtUtil.validateToken(token)){
            return false;
        }
        Admin admin = adminRepo.findByToken(token);
        if (admin == null) {
            return false;
        }
        return admin.getTokenExpiry().isAfter(LocalDateTime.now());
    }
}
