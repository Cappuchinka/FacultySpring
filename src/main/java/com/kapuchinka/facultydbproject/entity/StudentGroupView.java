package com.kapuchinka.facultydbproject.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Immutable
@NoArgsConstructor
public class StudentGroupView {
    @Id
    @NotNull
    @Column(name="row_number")
    private Short rowNumber;

    @NotNull
    @Column(name = "student_id")
    public Short studentId;

    @NotNull
    @Column(name="first_name")
    public String firstName;

    @NotNull
    @Column(name="last_name")
    public String lastName;

    @NotNull
    @Column(name="group_num")
    public Short groupNum;

    @NotNull
    @Column(name="group_year")
    public Short groupYear;
}
