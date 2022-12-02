package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.entity.Student;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Short> {
    @NotNull
    List<Student> findAll();
}
