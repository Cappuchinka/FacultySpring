package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.entity.Subject;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Short> {
    @NotNull
    List<Subject> findAll();
}
