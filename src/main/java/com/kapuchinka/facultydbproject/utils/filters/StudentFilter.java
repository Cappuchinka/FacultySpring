package com.kapuchinka.facultydbproject.utils.filters;

import com.kapuchinka.facultydbproject.entity.StudentGroupView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StudentFilter {
    private String firstName;
    private String lastName;

    public StudentFilter() {
        this.firstName = "";
        this.lastName = "";
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


    public List<StudentGroupView> filterStudents(List<StudentGroupView> students){

        if (!firstName.equals("")){
            Stream<StudentGroupView> stream = students.stream();
            students = stream.filter(s->s.firstName.split(" ")[0].equals(firstName)).toList();
        }

        if (!lastName.equals("")){
            Stream<StudentGroupView> stream = students.stream();
            students = stream.filter(s->s.lastName.split(" ")[0].equals(lastName)).toList();
        }

        return students;
    }

    public Page<StudentGroupView> filterStudents(Page<StudentGroupView> students){

        if (!firstName.equals("")){
            Stream<StudentGroupView> stream = students.stream();
            students = new PageImpl<>(stream.filter(s->s.firstName.split(" ")[0].equals(firstName)).toList());
        }

        if (!lastName.equals("")){
            Stream<StudentGroupView> stream = students.stream();
            students = new PageImpl<>(stream.filter(s->s.lastName.split(" ")[0].equals(lastName)).toList());
        }


        return students;
    }

    public Page<StudentGroupView> filterStudents(Iterable<StudentGroupView> students, Short offset, Short limit) {
        Stream<StudentGroupView> stream = StreamSupport.stream(students.spliterator(), false);
        List<StudentGroupView> studentGroupViewDtoList = stream.toList();
        studentGroupViewDtoList = filterStudents(studentGroupViewDtoList);
        Page<StudentGroupView> studentPage = new PageImpl<>(studentGroupViewDtoList, PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id")), studentGroupViewDtoList.size());
        return studentPage;
    }

    public boolean isReady(){
        return !Objects.equals(firstName, "") || !Objects.equals(lastName, "");
    }
}
