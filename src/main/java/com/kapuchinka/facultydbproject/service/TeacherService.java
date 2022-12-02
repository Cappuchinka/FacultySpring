package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.TeacherDto;
import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.entity.Teacher;
import com.kapuchinka.facultydbproject.repositories.SubjectRepository;
import com.kapuchinka.facultydbproject.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    private String findSubjectName(List<Subject> subjects, int subjectId) {
        String subjectName = "";
        for (Subject subject : subjects) {
            if (subject.getSubjectId() == subjectId) {
                subjectName = subject.getSubjectName();
            }
        }
        return subjectName;
    }

    public List<TeacherDto> getTeachers(List<Teacher> teachers, List<Subject> subjects) {
        int subjectId;
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setLastName(teachers.get(i).getLastName());
            teacherDto.setFirstName(teachers.get(i).getFirstName());
            subjectId = teachers.get(i).getSubjectId();
            teacherDto.setSubjectName(findSubjectName(subjects, subjectId));
            teacherDtos.add(teacherDto);
        }
        return teacherDtos;
    }


}