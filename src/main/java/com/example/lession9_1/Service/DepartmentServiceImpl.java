package com.example.lession9_1.Service;

import com.example.lession9_1.DTO.DepartmentDTO;
import com.example.lession9_1.Entity.Department;
import com.example.lession9_1.Repository.DepartmentRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepositoty departmentRepositoty;

    @Override
    public Department save(DepartmentDTO dto){
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        Department savedDepartment = departmentRepositoty.save(department);
        return savedDepartment;
    };
}