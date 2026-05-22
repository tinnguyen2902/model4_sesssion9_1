package com.example.lession9_1.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalService {

    // Đọc đường dẫn "uploads" từ file properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) {
        try {
            // 1. Tạo đường dẫn tuyệt đối tới thư mục uploads
            Path copyLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

            // 2. Nếu thư mục "uploads" chưa tồn tại trên máy, tự động tạo mới
            if (!Files.exists(copyLocation)) {
                Files.createDirectories(copyLocation);
            }

            // 3. Đổi tên file thành chuỗi ngẫu nhiên (UUID) để tránh bị trùng tên file trên máy
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String randomFileName = UUID.randomUUID().toString() + extension;

            // 4. Định vị file mục tiêu và tiến hành copy dữ liệu vào ổ cứng
            Path targetPath = copyLocation.resolve(randomFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 5. Trả về tên file (hoặc đường dẫn) để lưu vào database cột avatar_url
            return "/uploads/" + randomFileName;

        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu file vào máy: " + e.getMessage());
        }
    }
}