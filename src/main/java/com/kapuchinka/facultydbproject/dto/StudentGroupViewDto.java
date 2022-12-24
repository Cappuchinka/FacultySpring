package com.kapuchinka.facultydbproject.dto;

import lombok.Data;

@Data
public class StudentGroupViewDto {
    public Short studentId;
    public String firstName;
    public String lastName;
    public Short groupNum;
    public Short groupYear;
}
