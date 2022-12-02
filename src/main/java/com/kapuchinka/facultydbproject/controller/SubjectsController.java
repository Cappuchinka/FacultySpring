package com.kapuchinka.facultydbproject.controller;


import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.repositories.SubjectRepository;
import com.kapuchinka.facultydbproject.utils.StringPatterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SubjectsController {

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public String subjects(Model model) {
        List<Subject> subjects = subjectRepository.findAll();

        model.addAttribute("subjects", subjects);
        return "subjects/subjects";
    }

    @GetMapping("/subjects/add")
    public String addSubject(Model model) {
        String subjectName = StringPatterns.getTextPattern();
        model.addAttribute("subjectName", subjectName);
        return "subjects/subject-add";
    }

    @PostMapping("/subjects/add")
    public String addPostSubject(@RequestParam String subjectName) {
        if(!StringPatterns.isValidText(subjectName)){
            return "redirect:/subjects";
        }
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        subjectRepository.save(subject);
        return "redirect:/subjects";
    }

    @PostMapping("/subjects/{id}/remove")
    public String removeSubject(@PathVariable(value = "id") Short id) {
        if(!subjectRepository.existsById(id)){
            return "redirect:/subjects";
        }
        Subject consumer = subjectRepository.findById(id).orElseThrow();
        subjectRepository.delete(consumer);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/{id}/edit")
    public String editConsumer(@PathVariable(value = "id") Short id, Model model) {
        if(!subjectRepository.existsById(id)){
            return "redirect:/subjects";
        }
        Subject subject = subjectRepository.findById(id).orElseThrow();
        String subjectName = subject.getSubjectName();
        String subjectPattern = StringPatterns.getTextPattern();
        model.addAttribute("subjectPattern", subjectPattern);
        model.addAttribute("subjectName", subjectName);
        return "subjects/subject-edit";
    }

    @PostMapping("/subjects/{id}/edit")
    public String editPostConsumer(@PathVariable(value = "id") Short id, @RequestParam String subjectName) {
        if(!StringPatterns.isValidText(subjectName)){
            return "redirect:/subjects";
        }
        Subject subject = subjectRepository.findById(id).orElseThrow();
        System.out.println(subject.getSubjectId() + " " + subject.getSubjectName());
        subject.setSubjectName(subjectName);
        subjectRepository.save(subject);
        return "redirect:/subjects";
    }
}
