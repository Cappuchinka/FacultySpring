package com.kapuchinka.facultydbproject.service;

import com.kapuchinka.facultydbproject.dto.GroupDto;
import com.kapuchinka.facultydbproject.dto.StudentGroupViewDto;
import com.kapuchinka.facultydbproject.entity.Group;
import com.kapuchinka.facultydbproject.entity.Student;
import com.kapuchinka.facultydbproject.repositories.GroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final UnitService unitService;

    public GroupService(GroupRepository groupRepository,
                        StudentService studentService,
                        UnitService unitService) {
        this.groupRepository = groupRepository;
        this.studentService = studentService;
        this.unitService = unitService;
    }

    public List<GroupDto> listOfGroupDto() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDto> groupDtos = new ArrayList<>(groups.size());

        for (Group group : groups) {
            GroupDto groupDto = new GroupDto();
            groupDto.groupNum = group.groupNum;
            groupDto.groupSem = group.groupSem;
            groupDto.groupYear = group.groupYear;

            groupDtos.add(groupDto);
        }

        return groupDtos;
    }

    public List<String> stringList(List<Group> list) {
        List<String> newList = new ArrayList<>(list.size());

        for (Group l : list) {
            newList.add("Номер: " + l.groupNum + "; Семестр: " + l.groupSem + "; Год потока: " + l.groupYear);
        }

        return newList;
    }

    public void addGroupEntity(Group group) {
        groupRepository.save(group);
    }

    public List<Group> groups() {
        return groupRepository.findAll();
    }

    public boolean existsById(Short id) {
        return groupRepository.existsById(id);
    }

    public Group findById(Short id) {
        return groupRepository.findById(id).orElseThrow();
    }

    public void deleteGroup(Group group) {
        Short deleteId = group.groupId;
        studentService.deleteStudentByGroupId(deleteId);
        unitService.deleteUnitByGroupId(deleteId);
        groupRepository.delete(group);
    }

    public Page<Group> getGroupPage(Short offset, Short limit) {
        return groupRepository.findAll(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "groupId")));
    }
}
