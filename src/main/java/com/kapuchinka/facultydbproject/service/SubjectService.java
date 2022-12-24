package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.SubjectDto;
import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.repositories.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UnitService unitService;

    public SubjectService(SubjectRepository subjectRepository,
                          UnitService unitService) {
        this.subjectRepository = subjectRepository;
        this.unitService = unitService;
    }

    public List<SubjectDto> listOfSubjectsDto() {
        List<Subject> listOfSubjects = subjectRepository.findAll();
        List<SubjectDto> listSubjectDto = new ArrayList<>(listOfSubjects.size());

        for (Subject subjects : listOfSubjects) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.subjectName = subjects.subjectName;

            listSubjectDto.add(subjectDto);
        }

        return listSubjectDto;
    }

    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

    public List<Subject> subjects() {
        return subjectRepository.findAll();
    }

    public Short subjectId(String subjectName) {
        List<Subject> listOfSubjects = subjectRepository.findAll();
        for (Subject s: listOfSubjects) {
            if (s.subjectName.equals(subjectName)) {
                return s.subjectId;
            }
        }
        return -1;
    }

    public boolean existsById(Short id) {
        return subjectRepository.existsById(id);
    }

    public void deleteSubject(Subject subject) {
        Short deleteId = subject.subjectId;
        unitService.deleteUnitBySubjectId(deleteId);
        subjectRepository.delete(subject);
    }

    public Page<Subject> getSubjectPage(Short offset, Short limit){
        return subjectRepository.findAll(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "subjectId")));
    }

    public Subject findById(Short id) {
        return subjectRepository.findById(id).orElseThrow();
    }
}
