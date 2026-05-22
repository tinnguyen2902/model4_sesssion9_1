package com.example.lession9_1.Repository;

import com.example.lession9_1.Model.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepositoty extends JpaRepository<Department, Long> {

}
