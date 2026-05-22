package com.example.lession9_1.Controller;

import com.example.lession9_1.Model.DTO.ApiResponse;
import com.example.lession9_1.Model.DTO.DepartmentDTO;
import com.example.lession9_1.Model.Entity.Department;
import com.example.lession9_1.Service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService ds;

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> addDepartment(@Valid @RequestBody DepartmentDTO dto){
        Department savedDepartment= ds.save(dto);
        // form tạo thành công
        ApiResponse<Department> response = new ApiResponse<>();
        response.setStatus("SUCCESS");
        response.setMessage("Tạo phòng thành công ");
        response.setData(savedDepartment);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}