package com.uni.universitymanagement.config;

import com.uni.universitymanagement.entity.Course;
import com.uni.universitymanagement.entity.Department;
import com.uni.universitymanagement.entity.Student;
import com.uni.universitymanagement.repository.CourseRepository;
import com.uni.universitymanagement.repository.DepartmentRepository;
import com.uni.universitymanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Check if data already exists
            if (departmentRepository.count() > 0) {
                return;
            }

            String[] departmentNames = {
                    "Computer Science",
                    "Electrical Engineering",
                    "Mechanical Engineering",
                    "Civil Engineering",
                    "Business Administration"
            };

            String[] courseNames = {
                    "Data Structures", "Algorithms", "Database Systems", "Web Development", "Artificial Intelligence",
                    "Machine Learning", "Computer Networks", "Operating Systems", "Software Engineering", "Cloud Computing",
                    "Circuit Theory", "Power Systems", "Digital Electronics", "Microprocessors", "Signal Processing",
                    "Thermodynamics", "Fluid Mechanics", "Materials Science", "Manufacturing", "CAD Design",
                    "Structural Analysis", "Geotechnical Engineering", "Transportation", "Water Resources", "Construction",
                    "Accounting", "Finance", "Marketing", "Management", "Economics"
            };

            // Create 5 departments
            List<Department> departments = new ArrayList<>();
            for (String deptName : departmentNames) {
                Department dept = new Department(deptName);
                departments.add(dept);
            }
            List<Department> savedDepartments = departmentRepository.saveAll(departments);

            // Create 100 students (20 per department) with 5 courses each (500 total)
            List<Student> allStudents = new ArrayList<>();
            List<Course> allCourses = new ArrayList<>();

            for (int deptIdx = 0; deptIdx < savedDepartments.size(); deptIdx++) {
                Department dept = savedDepartments.get(deptIdx);

                // 20 students per department
                for (int studentIdx = 1; studentIdx <= 20; studentIdx++) {
                    String studentName = "Student_" + (deptIdx * 20 + studentIdx);
                    Student student = new Student(studentName, dept);
                    allStudents.add(student);
                }
            }

            List<Student> savedStudents = studentRepository.saveAll(allStudents);

            // Create 5 courses for each student
            for (int studentIdx = 0; studentIdx < savedStudents.size(); studentIdx++) {
                Student student = savedStudents.get(studentIdx);

                // 5 courses per student
                for (int courseIdx = 0; courseIdx < 5; courseIdx++) {
                    String courseTitle = courseNames[(studentIdx * 5 + courseIdx) % courseNames.length];
                    Course course = new Course(courseTitle, student);
                    allCourses.add(course);
                }
            }

            courseRepository.saveAll(allCourses);
        };
    }
}

