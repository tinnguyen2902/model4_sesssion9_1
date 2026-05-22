package com.example.lession9_1.Controller;

import com.example.lession9_1.Model.DTO.CandidateDTO;
import com.example.lession9_1.Model.Entity.Candidate;
import com.example.lession9_1.Service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Candidate> applyJob(@Valid @ModelAttribute CandidateDTO dto) {
        // Gọi xuống tầng Service để xử lý validate dữ liệu, lưu file và quản lý transaction
        Candidate savedCandidate = candidateService.applyJob(dto);

        // Trả về kết quả thành công với HTTP Status 201 Created kèm thông tin Candidate đã lưu
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCandidate);
    }
}