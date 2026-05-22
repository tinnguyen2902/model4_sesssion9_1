package com.example.lession9_1.Model.DTO;

import jakarta.validation.constraints.*;

public class EmployeeDTO {
    @NotBlank(message = "Tên không được trống")
    private String fullName;
    @NotBlank(message = "Email không được trống")
    @Email(message = "Email phải đúng định dạng", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotBlank(message = "Số điện thoại không được trống")
    @Pattern(message = "Số điện thoại không trống",regexp = "^0[35789]\\d{8}$")
    private String phone;
    @Min( value = 0, message = "Lương phải lớn hơn 0.")
    private Integer salary;
    @NotNull
    private Long departmentId;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String fullName, String email, String phone, Integer salary, Long departmentId) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}