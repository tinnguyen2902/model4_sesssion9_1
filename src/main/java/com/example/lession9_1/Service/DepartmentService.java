package com.example.lession9_1.Service;

import com.example.lession9_1.Model.DTO.DepartmentDTO;
import com.example.lession9_1.Model.Entity.Department;



public interface DepartmentService {
    Department save(DepartmentDTO dto);

}
