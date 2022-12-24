package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.entity.*;
import com.kapuchinka.facultydbproject.service.TeacherSubjectsViewService;
import com.kapuchinka.facultydbproject.utils.StringPatterns;
import com.kapuchinka.facultydbproject.utils.filters.TeacherFilter;
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
public class TeacherSubjectController {

    @Autowired
    private TeacherSubjectsViewService teacherSubjectsViewService;

    private final TeacherFilter filter = new TeacherFilter();

    @GetMapping("/teachers")
    public String teachers(@RequestParam(value = "offset", defaultValue = "0") Short offset,
                           @RequestParam(value = "limit", defaultValue = "10") Short limit,
                           Model model) {

        Page<TeacherSubjectView> teachers = teacherSubjectsViewService.getTeacherPage(offset, limit);

        if (filter.isReady()) {
            teachers = filter.filterTeachers(teacherSubjectsViewService.listOfTeachersSubjectsDto(), offset, limit);
        }

        Short size = (short) teachers.getSize();

        int totalPages = teachers.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("firstNameFilter", filter.getFirstName());
        model.addAttribute("lastNameFilter", filter.getLastName());
        model.addAttribute("subjectNameFilter", filter.getSubjectName());

        String namePattern = StringPatterns.getTextPattern();
        model.addAttribute("namePattern", namePattern);
        model.addAttribute("count", size);
        model.addAttribute("teachers", teachers);

        return "teachers/teachers";
    }

    @PostMapping("/teachers")
    public String teacherFilter(@RequestParam String firstNameFilter,
                                @RequestParam String lastNameFilter,
                                @RequestParam String subjectNameFilter, Model model){
        filter.setFirstName(firstNameFilter);
        filter.setLastName(lastNameFilter);
        filter.setSubjectName(subjectNameFilter);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/add")
    public String addTeacher(Model model) {
        String firstName = StringPatterns.getTextPattern();
        String lastName = StringPatterns.getTextPattern();
        List<Subject> subjectsList = teacherSubjectsViewService.listSubjects();
        String subjectName = StringPatterns.getTextPattern();
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("subjectsList", subjectsList);
        model.addAttribute("subjectName", subjectName);
        return "teachers/teacher-add";
    }

    @PostMapping("/teachers/add")
    public String addPostTeacher(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String subjectName) {
        if(!StringPatterns.isValidText(firstName) || !StringPatterns.isValidText(lastName) || !StringPatterns.isValidText(subjectName)){
            return "redirect:/teachers";
        }
        Teacher teacher = new Teacher();
        teacher.firstName = firstName;
        teacher.lastName = lastName;

        teacherSubjectsViewService.addTeacher(teacher);

        Unit unit = new Unit();
        unit.subjectId = teacherSubjectsViewService.subjectId(subjectName);
        unit.groupId = (short) 1;
        unit.teacherId = teacherSubjectsViewService.lastId();
        teacherSubjectsViewService.addUnit(unit);

        return "redirect:/teachers";
    }

    @PostMapping("/teachers/{id}/remove")
    public String removeTeacher(@PathVariable(value = "id") Short id) {
        if(!teacherSubjectsViewService.existsById(id)){
            return "redirect:/teachers";
        }
        Teacher teacher = teacherSubjectsViewService.findById(id);

        teacherSubjectsViewService.deleteTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/{id}/edit")
    public String editTeacher(@PathVariable(value = "id") Short id, Model model) {
        if(!teacherSubjectsViewService.existsById(id)){
            return "redirect:/teachers";
        }
        Teacher teacher = teacherSubjectsViewService.findById(id);
        String firstName = teacher.firstName;
        String lastName = teacher.lastName;
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
        Teacher teacher = teacherSubjectsViewService.findById(id);
        teacher.firstName = firstName;
        teacher.lastName = lastName;
        teacherSubjectsViewService.addTeacher(teacher);
        return "redirect:/teachers";
    }

}
