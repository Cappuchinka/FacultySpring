package com.kapuchinka.facultydbproject.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short unitId;
    private Short groupId;
    private Short teacherId;
    private Short subjectId;
}