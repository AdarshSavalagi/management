package com.sitmng.management.respository;

import com.sitmng.management.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface RequestsRepo extends MongoRepository<Request, String> {

    List<Request> findByDepartmentId(String departmentId);

    List<Request> findByStatus(int status);
}