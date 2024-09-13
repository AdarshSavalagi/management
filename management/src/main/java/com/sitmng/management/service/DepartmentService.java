package com.sitmng.management.service;


import com.sitmng.management.respository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sitmng.management.models.Department;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    DepartmentService(DepartmentRepo departmentRepo, PasswordEncoder passwordEncoder) {
        this.departmentRepo = departmentRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean deleteDepartment(String id) {
        Optional<Department> department = departmentRepo.findById(id);
        if (department.isPresent()) {
            departmentRepo.delete(department.get());
            return true;
        }
        return false;
    }

    public Department createDepartment(String name, String code, String password  )throws Exception{
        Department temp = departmentRepo.findByCode(code);
        if(temp != null){
            throw new Exception("Department Already Exists in DB");
        }
        Department department = new Department( passwordEncoder.encode(password), code,name);
        departmentRepo.save(department);
        return department;
    }

    public boolean verifyDepartmentLogin(Department department, String password){
        return passwordEncoder.matches(password,department.getPassword_hash());
    }

    public void saveDepartment(Department department) {
        departmentRepo.save(department);
    }

    public Optional<Department> getDepartment(String id ) {
        return departmentRepo.findById(id);
    }

   public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public Optional<Department> getDepartmentByCode(String departmentCode) {
        return Optional.ofNullable(departmentRepo.findByCode(departmentCode));
    }

    public Department getDepartmentByToken(String token) {
        return departmentRepo.findByToken(token);
    }
}
