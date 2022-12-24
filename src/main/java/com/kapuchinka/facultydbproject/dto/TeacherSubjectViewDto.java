package com.kapuchinka.facultydbproject.dto;

import lombok.Data;

@Data
public class TeacherSubjectViewDto {
    public Short teacherId;
    public String firstName;
    public String lastName;
    public String subjectName;
}
