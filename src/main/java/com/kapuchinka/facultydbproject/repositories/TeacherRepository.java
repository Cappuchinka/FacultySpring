package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.dto.TeacherDto;
import com.kapuchinka.facultydbproject.entity.Teacher;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Short> {
    @NotNull
    List<Teacher> findAll();

//    @Query("SELECT subj.subjectName " +
//            "FROM Subject subj WHERE subj.subjectId = :teacherId")
//    String getSubjectNameById(Short teacherId);

//    @Query("SELECT teach.lastName, teach.firstName, subj.subjectName " +
//            "FROM Teacher teach, Subject subj WHERE subj.subjectId = :teacherId")
//    TeacherDto getTeacher(Short teacherId);



}
