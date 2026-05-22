package com.example.lession9_1.Service;

import com.example.lession9_1.Exception.DuplicateResourceException;
import com.example.lession9_1.Exception.InvalidFileException;
import com.example.lession9_1.Exception.ResourceNotFoundException;
import com.example.lession9_1.Model.DTO.EmployeeDTO;
import com.example.lession9_1.Model.Entity.Department;
import com.example.lession9_1.Model.Entity.Employee;
import com.example.lession9_1.Repository.DepartmentRepositoty;
import com.example.lession9_1.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepositoty departmentRepositoty;

    @Autowired
    private LocalService localService; // Gọi đúng Service lưu local trên máy của bạn

    @Override
    public Employee uploadAvatar(Long id, MultipartFile file) {
        // 1. Kiểm tra Nhân viên tồn tại không
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với id: " + id));

        // 2. Kiểm tra file trống
        if (file.isEmpty()) {
            throw new InvalidFileException("Vui lòng chọn một file để upload");
        }

        // 3. Validate định dạng file dựa vào đuôi mở rộng (Extension) - Tránh lỗi Content-Type của Postman
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            throw new InvalidFileException("File không hợp lệ hoặc không có định dạng");
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png");

        if (!allowedExtensions.contains(extension)) {
            throw new InvalidFileException("Yêu cầu chỉ upload file ảnh (.jpg, .jpeg, .png)");
        }

        // 4. Validate kích thước file (< 2MB = 2 * 1024 * 1024 bytes)
        long maxSize = 2 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new InvalidFileException("Kích thước file không được vượt quá 2MB");
        }

        try {
            // 5. Tiến hành lưu file xuống thư mục local thông qua LocalService
            String url = localService.uploadFile(file);

            // 6. Lưu URL đường dẫn ảnh vào Database
            employee.setAvatarUrl(url);
            return employeeRepository.save(employee);

        } catch (Exception e) {
            throw new RuntimeException("Có lỗi xảy ra trong quá trình lưu file hệ thống: " + e.getMessage());
        }
    }

    @Override
    public Employee save(EmployeeDTO dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email " + dto.getEmail() + " đã tồn tại");
        }

        // Tìm kiếm phòng ban
        Department department = departmentRepositoty.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với id: " + dto.getDepartmentId()));

        // Nạp dữ liệu vào entity mới
        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }
}