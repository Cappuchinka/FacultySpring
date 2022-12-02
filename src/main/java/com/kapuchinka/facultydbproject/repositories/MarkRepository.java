package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.entity.Mark;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Short> {
    @NotNull
    List<Mark> findAll();
}
