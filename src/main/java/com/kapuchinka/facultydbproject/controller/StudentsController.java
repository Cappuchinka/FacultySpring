package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class StudentsController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public String students(Model model) {
        model.addAttribute("title", "Студенты факультета");
        return "students";
    }
}
