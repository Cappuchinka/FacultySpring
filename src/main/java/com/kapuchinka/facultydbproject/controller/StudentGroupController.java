package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.entity.*;
import com.kapuchinka.facultydbproject.service.GroupService;
import com.kapuchinka.facultydbproject.service.StudentGroupViewService;
import com.kapuchinka.facultydbproject.utils.StringPatterns;
import com.kapuchinka.facultydbproject.utils.filters.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class StudentGroupController {

    @Autowired
    private StudentGroupViewService studentGroupViewService;

    @Autowired
    private GroupService groupService;

    private final StudentFilter filter = new StudentFilter();


    @GetMapping("/students")
    public String students(@RequestParam(value = "offset", defaultValue = "0") Short offset,
                           @RequestParam(value = "limit", defaultValue = "50") Short limit,
                           Model model) {
        Page<StudentGroupView> students = studentGroupViewService.getStudentPage(offset, limit);

        if (filter.isReady()) {
            students = filter.filterStudents(studentGroupViewService.listOfStudentsGroupsDto(), offset, limit);
        }

        Short size = (short) students.getSize();

        int totalPages = students.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("firstNameFilter", filter.getFirstName());
        model.addAttribute("lastNameFilter", filter.getLastName());

        String namePattern = StringPatterns.getTextPattern();
        model.addAttribute("namePattern", namePattern);
        model.addAttribute("count", size);
        model.addAttribute("students", students);
        return "students/students";
    }

    @PostMapping("/students")
    public String studentFilter(@RequestParam String firstNameFilter, @RequestParam String lastNameFilter, Model model){
        filter.setFirstName(firstNameFilter);
        filter.setLastName(lastNameFilter);
        return "redirect:/students";
    }

    @GetMapping("/students/add")
    public String addStudent(Model model) {
        String firstName = StringPatterns.getTextPattern();
        String lastName = StringPatterns.getTextPattern();
        List<Group> groupList = groupService.groups();
        String groupId = StringPatterns.getNumberPattern();
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("groupList", groupList);
        model.addAttribute("groupId", groupId);
        return "students/student-add";
    }

    @PostMapping("/students/add")
    public String addPostStudent(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String groupId) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName) || !StringPatterns.isValidNumbers(groupId)){
            return "redirect:/students";
        }
        Student student = new Student();
        student.firstName = firstName;
        student.lastName = lastName;
        student.groupId = Short.parseShort(groupId);

        studentGroupViewService.addStudent(student);

        return "redirect:/students";
    }

    @PostMapping("/students/{id}/remove")
    public String removeStudent(@PathVariable(value = "id") Short id) {
        if(!studentGroupViewService.existsById(id)){
            return "redirect:/students";
        }
        Student student = studentGroupViewService.findById(id);

        studentGroupViewService.deleteStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}/edit")
    public String editStudent(@PathVariable(value = "id") Short id, Model model) {
        if(!studentGroupViewService.existsById(id)){
            return "redirect:/students";
        }
        Student student = studentGroupViewService.findById(id);
        String firstName = student.firstName;
        String lastName = student.lastName;
        String subjectPattern = StringPatterns.getTextPattern();
        model.addAttribute("subjectPattern", subjectPattern);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        return "students/student-edit";
    }

    @PostMapping("/students/{id}/edit")
    public String editPostStudent(@PathVariable(value = "id") Short id, @RequestParam String firstName, @RequestParam String lastName) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName)){
            return "redirect:/students";
        }
        Student student = studentGroupViewService.findById(id);
        student.firstName = firstName;
        student.lastName = lastName;
        studentGroupViewService.addStudent(student);
        return "redirect:/students";
    }
}
