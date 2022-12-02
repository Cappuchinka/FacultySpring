package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.dto.TeacherDto;
import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.entity.Teacher;
import com.kapuchinka.facultydbproject.repositories.SubjectRepository;
import com.kapuchinka.facultydbproject.repositories.TeacherRepository;
import com.kapuchinka.facultydbproject.service.TeacherService;
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
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private final TeacherService teacherService = new TeacherService(teacherRepository, subjectRepository);

    @GetMapping("/teachers")
    public String teachers(Model model) {
        List<Teacher> teachers = teacherRepository.findAll();
        List<Subject> subjects = subjectRepository.findAll();
        List<TeacherDto> teachersDtos = teacherService.getTeachers(teachers, subjects);
        model.addAttribute("teachersDtos", teachersDtos);
        model.addAttribute("teachers", teachers);
        return "teachers/teachers";
    }

    @PostMapping("/teachers/{id}/remove")
    public String removeTeacher(@PathVariable(value = "id") Short id) {
        if(!teacherRepository.existsById(id)){
            return "redirect:/teachers";
        }
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        teacherRepository.delete(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/{id}/edit")
    public String editTeacher(@PathVariable(value = "id") Short id, Model model) {
        if(!teacherRepository.existsById(id)){
            return "redirect:/teachers";
        }
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        String firstName = teacher.getFirstName();
        String lastName = teacher.getLastName();
        String subjectPattern = StringPatterns.getTextPattern();
        model.addAttribute("subjectPattern", subjectPattern);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "teachers/teacher-edit";
    }

    @PostMapping("/teachers/{id}/edit")
    public String editPostTeacher(@PathVariable(value = "id") Short id, @RequestParam String firstName, @RequestParam String lastName) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName)){
            return "redirect:/teachers";
        }
        Teacher teacher = teacherRepository.findById(id).orElseThrow();
        System.out.println(teacher.getSubjectId() + " " + teacher.getFirstName() + " " + teacher.getLastName());
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/add")
    public String addTeacher(Model model) {
        String firstName = StringPatterns.getTextPattern();
        String lastName = StringPatterns.getTextPattern();
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "teachers/teacher-add";
    }

    @PostMapping("/teachers/add")
    public String addPostTeacher(@RequestParam String firstName, @RequestParam String lastName) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName)){
            return "redirect:/teachers";
        }
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setSubjectId((short) (Math.random() * subjectRepository.findAll().size()));
        
        teacherRepository.save(teacher);

        return "redirect:/teachers";
    }
}
