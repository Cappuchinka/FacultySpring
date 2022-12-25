package com.kapuchinka.facultydbproject.utils.filters;

import com.kapuchinka.facultydbproject.entity.TeacherSubjectView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TeacherFilter {
    private String firstName;
    private String lastName;
    private String subjectName;

    public TeacherFilter() {
        this.firstName = "";
        this.lastName = "";
        this.subjectName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<TeacherSubjectView> filterTeachers(List<TeacherSubjectView> teachers){

        if (!firstName.equals("")){
            Stream<TeacherSubjectView> stream = teachers.stream();
            teachers = stream.filter(s->s.firstName.split(" ")[0].equals(firstName)).toList();
        }

        if (!lastName.equals("")){
            Stream<TeacherSubjectView> stream = teachers.stream();
            teachers = stream.filter(s->s.lastName.split(" ")[0].equals(lastName)).toList();
        }

        if (!subjectName.equals("")){
            Stream<TeacherSubjectView> stream = teachers.stream();
            teachers = stream.filter(s->s.subjectName.split(" ")[0].equals(subjectName)).toList();
        }

        return teachers;
    }

    public Page<TeacherSubjectView> filterTeachers(Page<TeacherSubjectView> teachers){

        if (!firstName.equals("")){
            Stream<TeacherSubjectView> stream = teachers.stream();
            teachers = new PageImpl<>(stream.filter(s->s.firstName.split(" ")[0].equals(firstName)).toList());
        }

        if (!lastName.equals("")){
            Stream<TeacherSubjectView> stream = teachers.stream();
            teachers = new PageImpl<>(stream.filter(s->s.lastName.split(" ")[0].equals(lastName)).toList());
        }

        if (!subjectName.equals("")){
            Stream<TeacherSubjectView> stream = teachers.stream();
            teachers = new PageImpl<>(stream.filter(s->s.subjectName.split(" ")[0].equals(subjectName)).toList());
        }


        return teachers;
    }

    public Page<TeacherSubjectView> filterTeachers(Iterable<TeacherSubjectView> teachers, Short offset, Short limit) {
        Stream<TeacherSubjectView> stream = StreamSupport.stream(teachers.spliterator(), false);
        List<TeacherSubjectView> teacherSubjectViewDtos = stream.toList();
        teacherSubjectViewDtos = filterTeachers(teacherSubjectViewDtos);
        Page<TeacherSubjectView> teacherPage = new PageImpl<>(teacherSubjectViewDtos, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id")), teacherSubjectViewDtos.size());
        return teacherPage;
    }

    public boolean isReady(){
        return !Objects.equals(firstName, "") || !Objects.equals(lastName, "") || !Objects.equals(subjectName, "");
    }
}
