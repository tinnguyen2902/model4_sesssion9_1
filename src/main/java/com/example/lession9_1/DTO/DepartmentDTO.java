package com.example.lession9_1.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartmentDTO {
   @NotBlank
   @Size(min = 5,max = 50,message = "Độ dài tên từ 5-50 kí tự và không được trống")
   private String name;

   @Size(max = 100 , message = "Độ dài không quá 100 kí tự")
   private String description;

    public DepartmentDTO() {
    }

    public DepartmentDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}