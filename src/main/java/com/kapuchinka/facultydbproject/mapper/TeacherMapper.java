package com.kapuchinka.facultydbproject.mapper;

import com.kapuchinka.facultydbproject.dto.TeacherDto;
import com.kapuchinka.facultydbproject.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    Teacher toEntity(TeacherDto teacherDto);
}
