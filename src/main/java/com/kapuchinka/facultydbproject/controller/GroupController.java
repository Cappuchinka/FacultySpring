package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.entity.Group;
import com.kapuchinka.facultydbproject.service.GroupService;
import com.kapuchinka.facultydbproject.utils.StringPatterns;
import com.kapuchinka.facultydbproject.utils.filters.GroupFilter;
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
public class GroupController {
    @Autowired
    private GroupService groupService;

    private final GroupFilter filter = new GroupFilter();

    @GetMapping("/groups")
    public String groups(@RequestParam(value = "offset", defaultValue = "0") Short offset,
                         @RequestParam(value = "limit", defaultValue = "5") Short limit,
                         Model model) {
        Page<Group> groups = groupService.getGroupPage(offset, limit);

        if (!filter.isReady()) {
            groups = filter.filter(groupService.groups(), offset, limit);
        }

        Short groupsSize = (short) groups.getSize();

        int totalPages = groups.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("numFilter", filter.getGroupNum());
        model.addAttribute("semFilter", filter.getGroupSem());
        model.addAttribute("yearFilter", filter.getGroupYear());

        String numberPattern = StringPatterns.getNumberPattern();
        model.addAttribute("numberPattern", numberPattern);
        model.addAttribute("size", groupsSize);
        model.addAttribute("groups", groups);


        return "groups/groups";
    }

    @PostMapping("/groups")
    public String groupFilter(@RequestParam String numFilter,
                              @RequestParam String semFilter,
                              @RequestParam String yearFilter,
                              Model model){
        filter.setGroupNum(Short.parseShort(numFilter));
        filter.setGroupSem(Short.parseShort(semFilter));
        filter.setGroupYear(Short.parseShort(yearFilter));
        return "redirect:/groups";
    }

    @GetMapping("/groups/add")
    public String addGroup(Model model) {
        String groupNum = StringPatterns.getNumberPattern();
        String groupSem = StringPatterns.getNumberPattern();
        String groupYear = StringPatterns.getNumberPattern();
        model.addAttribute("groupNum", groupNum);
        model.addAttribute("groupSem", groupSem);
        model.addAttribute("groupYear", groupYear);
        return "groups/groups-add";
    }

    @PostMapping("/groups/add")
    public String addPostGroup(@RequestParam String groupNum, @RequestParam String groupSem, @RequestParam String groupYear) {
        if(!(StringPatterns.isValidNumbers(groupNum) || StringPatterns.isValidNumbers(groupSem) || StringPatterns.isValidNumbers(groupYear))){
            return "redirect:/groups";
        }
        Group group = new Group();
        group.groupNum = Short.parseShort(groupNum);
        group.groupSem = Short.parseShort(groupSem);
        group.groupYear = Short.parseShort(groupYear);
        groupService.addGroupEntity(group);
        return "redirect:/groups";
    }

    @PostMapping("/groups/{id}/remove")
    public String removeGroup(@PathVariable(value = "id") Short id) {
        if(!groupService.existsById(id)){
            return "redirect:/groups";
        }
        Group group = groupService.findById(id);
        groupService.deleteGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/groups/{id}/edit")
    public String editGroup(@PathVariable(value = "id") Short id, Model model) {
        if(!groupService.existsById(id)){
            return "redirect:/groups";
        }
        Group group = groupService.findById(id);
        String groupNum = group.groupNum.toString();
        String groupSem = group.groupSem.toString();
        String groupYear = group.groupYear.toString();
        String subjectPattern = StringPatterns.getNumberPattern();
        model.addAttribute("subjectPattern", subjectPattern);
        model.addAttribute("groupNum", groupNum);
        model.addAttribute("groupSem", groupSem);
        model.addAttribute("groupYear", groupYear);
        return "groups/groups-edit";
    }

    @PostMapping("/groups/{id}/edit")
    public String editPostGroup(@PathVariable(value = "id") Short id,
                                  @RequestParam String groupNum,
                                  @RequestParam String groupSem,
                                  @RequestParam String groupYear) {
        if(!(StringPatterns.isValidNumbers(groupNum) || StringPatterns.isValidNumbers(groupSem) || StringPatterns.isValidNumbers(groupYear))){
            return "redirect:/groups";
        }
        Group group = groupService.findById(id);
        group.groupNum = Short.parseShort(groupNum);
        group.groupSem = Short.parseShort(groupSem);
        group.groupYear = Short.parseShort(groupYear);
        groupService.addGroupEntity(group);

        return "redirect:/groups";
    }
}
