package com.sitmng.management.controller;


import com.sitmng.management.dto.RequestDTO;
import com.sitmng.management.models.Department;
import com.sitmng.management.models.Request;
import com.sitmng.management.service.AdminService;
import com.sitmng.management.service.DepartmentService;
import com.sitmng.management.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
    private final RequestService requestService;
    private final AdminService adminService;
    private final DepartmentService departmentService;

    public RequestController(RequestService requestService, AdminService adminService ,DepartmentService departmentService) {
        this.requestService = requestService;
        this.adminService = adminService;
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> createRequest(@RequestHeader String Authorization, @RequestBody RequestDTO request) {
        Map<String,Object> response = new HashMap<>();
        try{
            System.out.println(Authorization.split(" ")[1]);
            Department department = departmentService.getDepartmentByToken(Authorization.split(" ")[1]);
            if (department == null) {
                response.put("status", "Invalid Department Request");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Request obj = requestService.createRequest(department.getId(),request.getTitle(),request.getSubject(),request.getDescription(),request.getIssued());
            response.put("status", "Success");
            response.put("data", obj);
            response.put("message", "your request has been created");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-department-requests")
    public ResponseEntity<List<Request>> getDepartmentRequests(@RequestHeader String Authorization) {
        try{
            Department department = departmentService.getDepartmentByToken(Authorization.split(" ")[1]);
            if (department == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Request> requestList = requestService.getRequestsByDepartmentId(department.getId());
            return new ResponseEntity<>(requestList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Request>> getRequests(@RequestHeader String Authorization) {
    try {
        String token = Authorization.split(" ")[1];
        if(adminService.verifyAdminByToken(token)){
            List<Request> requests = requestService.getAllRequests();
            return new ResponseEntity<>(requests, HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateRequest(@RequestHeader String Authorization, @PathVariable String id, @RequestBody Map<String,Integer> req) {
        Map<String,String> response = new HashMap<>();
        try{
            if (adminService.verifyAdminByToken(Authorization.split(" ")[1])) {
                requestService.updateStatusById(id, req.get("status"));
                response.put("status", "success");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message","token is not valid");
            response.put("status",HttpStatus.UNAUTHORIZED.toString());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", "fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteRequest(@RequestHeader String Authorization, @PathVariable String id) {
        Map<String,String> response = new HashMap<>();
        try{
            if (adminService.verifyAdminByToken(Authorization.split(" ")[1])) {
                requestService.deleteRequestById(id);
                response.put("status", "success");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.put("message","token is not valid");
            response.put("status",HttpStatus.UNAUTHORIZED.toString());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            response.put("message", e.getMessage());
            response.put("status", "fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
