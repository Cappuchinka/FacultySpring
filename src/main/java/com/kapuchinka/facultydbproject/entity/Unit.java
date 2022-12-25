package com.kapuchinka.facultydbproject.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Setter
@Getter
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Short unitId;
    public Short groupId;
    public Short teacherId;
    public Short subjectId;

    public Unit(Short groupId, Short teacherId, Short subjectId) {
        this.groupId = groupId;
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public Unit() {
    }
}
