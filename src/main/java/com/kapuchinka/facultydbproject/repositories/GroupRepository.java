package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.entity.Group;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Short> {
    @NotNull
    List<Group> findAll();

//    @Query(value = "SELECT gr FROM Group gr")
//    List<Group> groups();
}
