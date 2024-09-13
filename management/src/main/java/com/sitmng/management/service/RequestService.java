package com.sitmng.management.service;

import com.sitmng.management.models.Request;
import com.sitmng.management.respository.RequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    RequestsRepo requestsRepo;

    @Autowired
    public RequestService(RequestsRepo requestsRepo) {
        this.requestsRepo = requestsRepo;
    }

    public Request createRequest(String departmentId, String title, String subject, String description ) {
        Request temp = new Request(departmentId, title, subject, description);
        return requestsRepo.save(temp);
    }

    public List<Request> getAllRequests(){
        return requestsRepo.findAll();
    }

    public List<Request> getAllRequestsByStatus(int status){
        return requestsRepo.findByStatus(status);
    }
    public Request getRequestById(String id) {
        return requestsRepo.findById(id).get();
    }

    public List<Request> getRequestsByDepartmentId(String departmentId) {
        return requestsRepo.findByDepartmentId(departmentId);
    }

    public boolean deleteRequestById(String id) {
        try{
            requestsRepo.delete(getRequestById(id));
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean updateStatusById(String id, int status) {
        Request request = getRequestById(id);
        if (request != null) {
            request.setStatus(status);
            requestsRepo.save(request);
            return true;
        }
        return false;

    }

}
