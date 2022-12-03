package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.GroupDto;
import com.kapuchinka.facultydbproject.dto.StudentDto;
import com.kapuchinka.facultydbproject.entity.Group;
import com.kapuchinka.facultydbproject.entity.Student;
import com.kapuchinka.facultydbproject.repositories.GroupRepository;
import com.kapuchinka.facultydbproject.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    private GroupDto findGroup(List<Group> groups, int groupId) {
        GroupDto groupDto = new GroupDto();
        for (Group group : groups) {
            if (group.getGroupId() == groupId) {
                groupDto.setGroupNum(group.getGroupNum());
                groupDto.setGroupSem(group.getGroupSem());
                groupDto.setGroupYear(group.getGroupYear());
            }
        }
        return groupDto;
    }

    public List<StudentDto> getStudents(List<Student> students, List<Group> groups) {
        List<StudentDto> studentDtos = new ArrayList<>(students.size());
        int groupId;
        GroupDto groupDto;
        for (Student student : students) {
            StudentDto studentDto = new StudentDto();
            studentDto.setFirstName(student.getFirstName());
            studentDto.setLastName(student.getLastName());
            groupId = student.getGroupId();
            groupDto = findGroup(groups, groupId);
            studentDto.setGroupNum(groupDto.getGroupNum());
            studentDto.setGroupYear(groupDto.getGroupYear());
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }
}
