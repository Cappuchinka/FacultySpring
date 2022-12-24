package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.entity.Student;
import com.kapuchinka.facultydbproject.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void deleteStudentByGroupId(Short groupId) {
        List<Student> students = studentRepository.findAll();
        for (Student s: students) {
            if (s.groupId.equals(groupId)) {
                studentRepository.delete(s);
            }
        }
    }
}
