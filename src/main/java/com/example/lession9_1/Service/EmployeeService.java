package com.example.lession9_1.Service;

import com.example.lession9_1.Model.DTO.EmployeeDTO;
import com.example.lession9_1.Model.Entity.Employee;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    Employee save(EmployeeDTO dto);
    //LS5
    Employee uploadAvatar(Long id, MultipartFile file);
}