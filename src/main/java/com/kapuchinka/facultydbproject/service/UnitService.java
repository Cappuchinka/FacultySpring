package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.entity.Unit;
import com.kapuchinka.facultydbproject.repositories.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
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
}
