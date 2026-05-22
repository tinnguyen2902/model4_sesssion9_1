package com.example.lession9_1.Repository;

import com.example.lession9_1.Model.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
   boolean existsByEmail(String email);   // check email trùng
}