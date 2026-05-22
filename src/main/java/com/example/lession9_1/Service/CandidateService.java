package com.example.lession9_1.Service;

import com.example.lession9_1.Model.DTO.CandidateDTO;
import com.example.lession9_1.Model.Entity.Candidate;

public interface CandidateService {
    Candidate applyJob(CandidateDTO dto);
}