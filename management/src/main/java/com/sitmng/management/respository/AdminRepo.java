package com.sitmng.management.respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.sitmng.management.models.Admin;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Admin findByUsername(String username);
    Admin findByToken(String token);
}
