package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.entity.Unit;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Short> {
    @NotNull
    List<Unit> findAll();
}
