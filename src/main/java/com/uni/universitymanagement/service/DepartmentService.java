package com.uni.universitymanagement.service;

import com.uni.universitymanagement.dto.CourseDTO;
import com.uni.universitymanagement.dto.DepartmentDTO;
import com.uni.universitymanagement.dto.StudentDTO;
import com.uni.universitymanagement.entity.Department;
import com.uni.universitymanagement.entity.Student;
import com.uni.universitymanagement.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::convertToDepartmentDTO)
                .collect(Collectors.toList());
    }

    private DepartmentDTO convertToDepartmentDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setDepartmentName(department.getDepartmentName());

        if (department.getStudents() != null) {
            dto.setStudents(
                    department.getStudents()
                            .stream()
                            .map(this::convertToStudentDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private StudentDTO convertToStudentDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setStudentName(student.getStudentName());

        if (student.getCourses() != null) {
            dto.setCoursesCount(student.getCourses().size());
            dto.setCourses(
                    student.getCourses()
                            .stream()
                            .map(this::convertToCourseDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    private CourseDTO convertToCourseDTO(com.uni.universitymanagement.entity.Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        return dto;
    }
}
