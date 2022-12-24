package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.StudentGroupViewDto;
import com.kapuchinka.facultydbproject.dto.TeacherSubjectViewDto;
import com.kapuchinka.facultydbproject.entity.Student;
import com.kapuchinka.facultydbproject.entity.StudentGroupView;
import com.kapuchinka.facultydbproject.repositories.StudentGroupViewRepository;
import com.kapuchinka.facultydbproject.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentGroupViewService {
    private final StudentGroupViewRepository studentGroupViewRepository;
    private final StudentRepository studentRepository;

    public StudentGroupViewService(StudentGroupViewRepository studentGroupViewRepository, StudentRepository studentRepository) {
        this.studentGroupViewRepository = studentGroupViewRepository;
        this.studentRepository = studentRepository;
    }

    public List<StudentGroupView> listOfStudentsGroupsDto() {
       return studentGroupViewRepository.findAll();
    }

    public List<String> stringList(List<StudentGroupViewDto> list) {
        List<String> newList = new ArrayList<>(list.size());

        for (StudentGroupViewDto l : list) {
            newList.add(l.lastName + " " + l.firstName + ": " + l.groupNum + "_" + l.groupYear);
        }

        return newList;
    }

    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public boolean existsById(Short id) {
        return studentRepository.existsById(id);
    }

    public Student findById(Short id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    public List<Student> students() {
        return studentRepository.findAll();
    }

    public Page<StudentGroupView> getStudentPage(Short offset, Short limit) {
        return studentGroupViewRepository.findAll(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "studentId")));
    }
}
