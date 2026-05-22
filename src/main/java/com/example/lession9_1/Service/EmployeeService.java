package com.example.lession9_1.Service;

import com.example.lession9_1.Model.DTO.EmployeeDTO;
import com.example.lession9_1.Model.Entity.Employee;

public interface EmployeeService {
    Employee save(EmployeeDTO dto);
}