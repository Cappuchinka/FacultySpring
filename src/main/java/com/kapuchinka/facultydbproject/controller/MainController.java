package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.entity.Student;
import com.kapuchinka.facultydbproject.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public String index(Model model) {
//        List<Student> students = studentRepository.findAll();
//        String stud = students.get(0).getFirstName();
//        model.addAttribute("student", stud);
        return "index";
    }
}
