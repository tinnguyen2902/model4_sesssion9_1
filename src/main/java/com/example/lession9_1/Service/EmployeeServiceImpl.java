package com.example.lession9_1.Service;

import com.example.lession9_1.Model.DTO.EmployeeDTO;
import com.example.lession9_1.Model.Entity.Department;
import com.example.lession9_1.Model.Entity.Employee;
import com.example.lession9_1.Repository.DepartmentRepositoty;
import com.example.lession9_1.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepositoty  departmentRepositoty;
    @Override
    public Employee save(EmployeeDTO dto){
        Department department = departmentRepositoty.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với id: " + dto.getDepartmentId()));
       // nạp dữ liệu vào
        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }
}