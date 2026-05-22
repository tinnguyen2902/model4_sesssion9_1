package com.example.lession9_1.Service;

import com.example.lession9_1.Exception.DuplicateResourceException;
import com.example.lession9_1.Exception.InvalidFileException;
import com.example.lession9_1.Model.DTO.CandidateDTO;
import com.example.lession9_1.Model.Entity.Candidate;
import com.example.lession9_1.Repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private LocalService localService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Candidate applyJob(CandidateDTO dto) {

        // 1. Tận dụng logic kiểm tra trùng lặp email
        if (candidateRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email " + dto.getEmail() + " đã được sử dụng để ứng tuyển trước đó!");
        }

        // 2. Tận dụng logic kiểm tra file trống (MultipartFile)
        if (dto.getCvFile() == null || dto.getCvFile().isEmpty()) {
            throw new InvalidFileException("Vui lòng đính kèm tệp hồ sơ CV của bạn");
        }

        // 3. Tận dụng logic lấy Extension để validate - Nhưng đổi từ ảnh sang (.pdf)
        String originalFilename = dto.getCvFile().getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            throw new InvalidFileException("Tệp tin không hợp lệ hoặc không có định dạng");
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        List<String> allowedExtensions = Arrays.asList(".pdf"); // Chỉ chấp nhận đuôi PDF

        if (!allowedExtensions.contains(extension)) {
            throw new InvalidFileException("Yêu cầu chỉ upload file hồ sơ định dạng PDF (.pdf)");
        }

        // 4. Tận dụng logic kiểm tra kích thước file (< 2MB)
        long maxSize = 2 * 1024 * 1024;
        if (dto.getCvFile().getSize() > maxSize) {
            throw new InvalidFileException("Kích thước tệp CV không được vượt quá 2MB");
        }

        // 5. Khởi tạo Entity và nạp thông tin Text vào Database trước để lấy ID tự động sinh
        Candidate candidate = new Candidate();
        candidate.setName(dto.getName());
        candidate.setEmail(dto.getEmail());

        // Lưu tạm xuống database (Nhưng chưa commit vĩnh viễn)
        candidate = candidateRepository.save(candidate);

        try {
            // 6. Tận dụng lại hàm uploadFile của LocalService để đẩy file vào thư mục /uploads
            String fileUrl = localService.uploadFile(dto.getCvFile());

            // 7. Cập nhật đường dẫn URL nhận từ LocalService vào Database và hoàn tất giao dịch
            candidate.setCvUrl(fileUrl);
            return candidateRepository.save(candidate);

        } catch (Exception e) {
            throw new RuntimeException("Quá trình nộp hồ sơ thất bại do lỗi hệ thống lưu trữ: " + e.getMessage());
        }
    }
}