package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.TeacherSubjectViewDto;
import com.kapuchinka.facultydbproject.entity.Group;
import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.entity.Teacher;
import com.kapuchinka.facultydbproject.entity.TeacherSubjectView;
import com.kapuchinka.facultydbproject.entity.Unit;
import com.kapuchinka.facultydbproject.repositories.TeacherRepository;
import com.kapuchinka.facultydbproject.repositories.TeacherSubjectViewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherSubjectsViewService {
    private final TeacherSubjectViewRepository teacherSubjectViewRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final UnitService unitService;

    public TeacherSubjectsViewService(TeacherSubjectViewRepository teacherSubjectViewRepository,
                                      TeacherRepository teacherRepository,
                                      SubjectService subjectService,
                                      GroupService groupService,
                                      UnitService unitService) {
        this.teacherSubjectViewRepository = teacherSubjectViewRepository;
        this.teacherRepository = teacherRepository;
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.unitService = unitService;
    }

    public List<TeacherSubjectView> listOfTeachersSubjectsDto() {
        return teacherSubjectViewRepository.findAll();
    }

    public List<String> stringList(List<TeacherSubjectViewDto> list) {
        List<String> newList = new ArrayList<>(list.size());

        for (TeacherSubjectViewDto l : list) {
            newList.add(l.lastName + " " + l.firstName + ": " + l.subjectName);
        }

        return newList;
    }

    public List<Subject> listSubjects() {
        return subjectService.subjects();
    }

    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public Short lastId() {
        List<Teacher> list = teacherRepository.findAll();
        return list.get(list.size() - 1).teacherId;
    }

    public void addUnit(Unit unit) {
        unitService.addUnit(unit);
    }

    public Short subjectId(String subjectName) {
        return subjectService.subjectId(subjectName);
    }

    public boolean existsById(Short id) {
        return teacherRepository.existsById(id);
    }

    public Teacher findById(Short id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public void deleteTeacher(Teacher teacher) {
        Short deleteId = teacher.teacherId;
        unitService.deleteUnitByTeacherId(deleteId);
        teacherRepository.delete(teacher);
    }

    public List<Teacher> teachers() {
        return teacherRepository.findAll();
    }

    public Page<TeacherSubjectView> getTeacherPage(Short offset, Short limit) {
        return teacherSubjectViewRepository.findAll(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "teacherId")));
    }
}
