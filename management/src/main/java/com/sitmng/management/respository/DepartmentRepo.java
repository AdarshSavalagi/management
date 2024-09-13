package com.sitmng.management.respository;

import com.sitmng.management.models.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepo extends MongoRepository<Department,String> {
    Department findByCode(String code);
    Department findByToken(String token);
}
