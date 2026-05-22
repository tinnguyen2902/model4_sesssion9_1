package com.example.lession9_1.Controller;

import com.example.lession9_1.Model.DTO.EmployeeDTO;
import com.example.lession9_1.Model.Entity.Employee;
import com.example.lession9_1.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
        @Autowired
        private EmployeeService employeeService;

        @PostMapping
        public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeDTO dto){
            Employee savedEmployee = employeeService.save(dto);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        }
}