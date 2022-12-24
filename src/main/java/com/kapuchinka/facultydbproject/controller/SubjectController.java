package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.entity.Subject;
import com.kapuchinka.facultydbproject.service.SubjectService;
import com.kapuchinka.facultydbproject.utils.StringPatterns;
import com.kapuchinka.facultydbproject.utils.filters.SubjectFilter;
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
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    private final SubjectFilter filter = new SubjectFilter();

    @GetMapping("/subjects")
    public String subjects(@RequestParam(value = "offset", defaultValue = "0") Short offset,
                           @RequestParam(value = "limit", defaultValue = "3") Short limit,
                           Model model) {
        Page<Subject> subjectDtos = subjectService.getSubjectPage(offset, limit);

        if (filter.isReady()){
            subjectDtos = filter.filter(subjectService.subjects(), offset, limit);
        }
        Short size = (short) subjectDtos.getSize();

        int totalPages = subjectDtos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("nameFilter", filter.getSubjectName());

        String namePattern = StringPatterns.getTextPattern();
        model.addAttribute("namePattern", namePattern);

        model.addAttribute("count", size);
        model.addAttribute("subjectDtos", subjectDtos);
        return "subjects/subjects";
    }

    @PostMapping("/subjects")
    public String subjectFilter(@RequestParam String nameFilter, Model model){
        filter.setSubjectName(nameFilter);
        return "redirect:/subjects";
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
        subject.subjectName = subjectName;
        subjectService.addSubject(subject);
        return "redirect:/subjects";
    }

    @PostMapping("/subjects/{id}/remove")
    public String removeSubject(@PathVariable(value = "id") Short id) {
        if(!subjectService.existsById(id)){
            return "redirect:/subjects";
        }
        Subject subject = subjectService.findById(id);

        subjectService.deleteSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/{id}/edit")
    public String editSubject(@PathVariable(value = "id") Short id, Model model) {
        if(!subjectService.existsById(id)){
            return "redirect:/subjects";
        }
        Subject subject = subjectService.findById(id);
        String subjectName = subject.subjectName;
        String subjectPattern = StringPatterns.getTextPattern();
        model.addAttribute("subjectPattern", subjectPattern);
        model.addAttribute("subjectName", subjectName);
        return "subjects/subject-edit";
    }

    @PostMapping("/subjects/{id}/edit")
    public String editPostSubject(@PathVariable(value = "id") Short id, @RequestParam String subjectName) {
        if(!StringPatterns.isValidText(subjectName)){
            return "redirect:/subjects";
        }
        Subject subject = subjectService.findById(id);
        subject.subjectName = subjectName;
        subjectService.addSubject(subject);
        return "redirect:/subjects";
    }
}
