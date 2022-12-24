package com.kapuchinka.facultydbproject.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Immutable
public class TeacherSubjectView {
    @Id
    @Column(name="row_number")
    private Short rowNumber;
    @NotNull
    @Column(name="teacher_id")
    public Short teacherId;
    @NotNull
    @Column(name="first_name")
    public String firstName;
    @NotNull
    @Column(name="last_name")
    public String lastName;
    @NotNull
    @Column(name = "subject_name")
    public String subjectName;
}
