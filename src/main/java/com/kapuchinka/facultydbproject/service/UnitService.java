package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.UnitDto;
import com.kapuchinka.facultydbproject.entity.Group;
import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.entity.Teacher;
import com.kapuchinka.facultydbproject.entity.Unit;
import com.kapuchinka.facultydbproject.repositories.GroupRepository;
import com.kapuchinka.facultydbproject.repositories.SubjectRepository;
import com.kapuchinka.facultydbproject.repositories.TeacherRepository;
import com.kapuchinka.facultydbproject.repositories.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    public List<Unit> units() {
        return unitRepository.findAll();
    }

    public List<UnitDto> unitDtos() {
        List<Unit> units = units();
        List<UnitDto> unitDtos = new ArrayList<>(units.size());

        for (Unit u: units) {
            UnitDto unitDto = new UnitDto();
            unitDto.unitId = u.unitId;
            Teacher teacher = teacherRepository.findById(u.teacherId).orElseThrow();
            Group group = groupRepository.findById(u.groupId).orElseThrow();
            Subject subject = subjectRepository.findById(u.subjectId).orElseThrow();
            unitDto.teacherName = teacher.lastName + ' ' + teacher.firstName;
            unitDto.subjectName = subject.subjectName;
            unitDto.groupName = group.groupNum.toString() + '_' + group.groupSem.toString() + '_' + group.groupYear.toString();
            unitDtos.add(unitDto);
        }
        return unitDtos;
    }

    public UnitService(UnitRepository unitRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository, GroupRepository groupRepository) {
        this.unitRepository = unitRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
    }

    public void addUnit(Unit unit) {
        unitRepository.save(unit);
    }

    public void deleteUnitByGroupId(Short deleteId) {
        List<Unit> unitList = unitRepository.findAll();
        for (Unit u: unitList) {
            if (u.groupId.equals(deleteId)) {
                unitRepository.delete(u);
            }
        }
    }

    public void deleteUnitBySubjectId(Short deleteId) {
        List<Unit> unitList = unitRepository.findAll();
        for (Unit u: unitList) {
            if (u.subjectId.equals(deleteId)) {
                unitRepository.delete(u);
            }
        }
    }

    public void deleteUnitByTeacherId(Short deleteId) {
        List<Unit> unitList = unitRepository.findAll();
        for (Unit u: unitList) {
            if (u.teacherId.equals(deleteId)) {
                unitRepository.delete(u);
            }
        }
    }

    public boolean existsById(Short id) {
        return unitRepository.existsById(id);
    }

    public Unit findById(Short id) {
        return unitRepository.findById(id).orElseThrow();
    }

    public void deleteUnit(Unit unit) {
        unitRepository.delete(unit);
    }

    public void save(Unit unit) {
        unitRepository.save(unit);
    }
}
