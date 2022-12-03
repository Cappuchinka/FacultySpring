package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.dto.StudentDto;
import com.kapuchinka.facultydbproject.entity.Group;
import com.kapuchinka.facultydbproject.entity.Student;
import com.kapuchinka.facultydbproject.entity.Teacher;
import com.kapuchinka.facultydbproject.repositories.GroupRepository;
import com.kapuchinka.facultydbproject.repositories.StudentRepository;
import com.kapuchinka.facultydbproject.service.StudentService;
import com.kapuchinka.facultydbproject.utils.StringPatterns;
import org.hibernate.annotations.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentsController {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    private final StudentService studentService = new StudentService(studentRepository, groupRepository);

    @GetMapping("/students")
    public String students(Model model) {
        List<Group> groups = groupRepository.findAll(Sort.by(Sort.Order.by("groupId")));
        List<Student> students = studentRepository.findAll(Sort.by(Sort.Order.by("studentId")));

        List<StudentDto> studentDtos = studentService.getStudents(students, groups);

        model.addAttribute("studentDtos", studentDtos);
        model.addAttribute("students", students);
        return "students/students";
    }

    @PostMapping("/students/{id}/remove")
    public String removeTeacher(@PathVariable(value = "id") Short id) {
        if(!studentRepository.existsById(id)){
            return "redirect:/students";
        }
        Student student = studentRepository.findById(id).orElseThrow();
        studentRepository.delete(student);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String editTeacher(@PathVariable(value = "id") Short id, Model model) {
        if(!studentRepository.existsById(id)){
            return "redirect:/students";
        }
        Student student = studentRepository.findById(id).orElseThrow();
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String subjectPattern = StringPatterns.getTextPattern();
        model.addAttribute("subjectPattern", subjectPattern);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "students/student-edit";
    }

    @PostMapping("/students/{id}/edit")
    public String editPostTeacher(@PathVariable(value = "id") Short id, @RequestParam String firstName, @RequestParam String lastName) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName)){
            return "redirect:/students";
        }
        Student student = studentRepository.findById(id).orElseThrow();
        System.out.println(student.getStudentId() + " " + student.getFirstName() + " " + student.getLastName());
        student.setFirstName(firstName);
        student.setLastName(lastName);
        studentRepository.save(student);
        return "redirect:/students";
    }

    @GetMapping("/students/add")
    public String addStudent(Model model) {
        String firstName = StringPatterns.getTextPattern();
        String lastName = StringPatterns.getTextPattern();
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "students/student-add";
    }

    @PostMapping("/students/add")
    public String addPostStudent(@RequestParam String firstName, @RequestParam String lastName) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName)){
            return "redirect:/students";
        }
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setGroupId((short) (Math.random() * groupRepository.findAll().size() + 1));
        studentRepository.save(student);

        return "redirect:/students";
    }
}
