package com.kapuchinka.facultydbproject.repositories;

import com.kapuchinka.facultydbproject.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Short> {
}
