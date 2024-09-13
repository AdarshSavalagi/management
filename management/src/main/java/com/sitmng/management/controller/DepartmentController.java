package com.sitmng.management.controller;

import com.sitmng.management.dto.LoginDTO;
import com.sitmng.management.dto.SignupDTO;
import com.sitmng.management.models.Department;
import com.sitmng.management.security.JwtUtil;
import com.sitmng.management.service.AdminService;
import com.sitmng.management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    final DepartmentService departmentService;
    final AdminService adminService;
    final JwtUtil jwtUtil;

    @Autowired
    public DepartmentController(DepartmentService departmentService, AdminService adminService, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.departmentService = departmentService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Map<String,String> map = new HashMap<>();
        try{
            Optional<Department> department  = departmentService.getDepartmentByCode(loginDTO.getUsername());
            if (department.isPresent()) {
                if (departmentService.verifyDepartmentLogin(department.get(), loginDTO.getPassword())){
                    map.put("status", "success");
                    String token = jwtUtil.generateToken(department.get().getId());
                    map.put("token", token);
                    Department temp = department.get();
                    temp.setToken(token);
                    LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
                    temp.setTokenExpires(expiryDate);
                    departmentService.saveDepartment(temp);
                    return ResponseEntity.status(HttpStatus.OK).body(map);
                }
                map.put("status", "fail");
                map.put("message","invalid password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
            }else{
                map.put("message","department not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
            }
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status","error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> create(@RequestHeader String Authorization, @RequestBody SignupDTO signupDTO) {
        Map<String,String> map = new HashMap<>();
        try{
            if (adminService.verifyAdminByToken(Authorization.split(" ")[1])){
                Department department = departmentService.createDepartment(signupDTO.getName(),signupDTO.getUsername(),signupDTO.getPassword());
                map.put("status", "success");
                return ResponseEntity.status(HttpStatus.CREATED).body(map);
            }
                map.put("status", "fail");
                map.put("message","invalid authorisation");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);

        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status","error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> delete(@RequestHeader String Authorization, @PathVariable String id) {
        Map<String,String> map = new HashMap<>();
        try{
            if ( adminService.verifyAdminByToken(Authorization.split(" ")[1])){
                departmentService.deleteDepartment(id);
                map.put("status","success");
                map.put("message","department deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).body(map);
            }
            map.put("status", "fail");
            map.put("message","invalid authorisation");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("status","error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Map<String,String>>> getAll(@RequestHeader String Authorization) {
        try{
      if ( adminService.verifyAdminByToken(Authorization.split(" ")[1])){
          List<Department> departments = departmentService.getAllDepartments();
          List<Map<String,String>> mapList = new ArrayList<>();
          for (Department department : departments) {
              Map<String,String> map = new HashMap<>();
              map.put("id", department.getId());
              map.put("name", department.getName());
              map.put("code", department.getCode());
              mapList.add(map);
          }
          return ResponseEntity.status(HttpStatus.OK).body(mapList);
      }
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

}
