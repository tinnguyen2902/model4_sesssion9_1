package com.example.lession9_1.Model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class CandidateDTO {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull(message = "Đính kèm theo tệp CV của bạn")
    private MultipartFile cvFile;

    public CandidateDTO() {
    }

    public CandidateDTO(String name, String email, MultipartFile cvFile) {
        this.name = name;
        this.email = email;
        this.cvFile = cvFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MultipartFile getCvFile() {
        return cvFile;
    }

    public void setCvFile(MultipartFile cvFile) {
        this.cvFile = cvFile;
    }
}