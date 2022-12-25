package com.kapuchinka.facultydbproject.controller;

import com.kapuchinka.facultydbproject.dto.UnitDto;
import com.kapuchinka.facultydbproject.entity.Unit;
import com.kapuchinka.facultydbproject.service.GroupService;
import com.kapuchinka.facultydbproject.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UnitController {
    @Autowired
    private UnitService unitService;

    @GetMapping("/units")
    public String units(Model model) {
        List<Unit> units = unitService.units();
        List<UnitDto> unitDtos = unitService.unitDtos();

        model.addAttribute("units", units);
        model.addAttribute("unitDtos", unitDtos);

        return "units/units";
    }

    @PostMapping("/units/{id}/remove")
    public String removeUnit(@PathVariable(value = "id") Short id, Model model) {
        if(!unitService.existsById(id)){
            return "redirect:/units";
        }
        Unit unit = unitService.findById(id);
        unitService.deleteUnit(unit);
        return "redirect:/units";
    }

    @GetMapping("/units/add")
    public String addUnit(Model model) {
        return "units/units-add";
    }

    @PostMapping("/units/add")
    public String addPostUnit(@RequestParam Short groupId,
                              @RequestParam Short teacherId,
                              @RequestParam Short subjectId,
                              Model model){
        Unit unit = new Unit(groupId, teacherId, subjectId);
        unitService.save(unit);
        return "redirect:/units";
    }

    @GetMapping("/units/{id}/edit")
    public String editUnit(@PathVariable(value = "id") Short id, Model model){
        if(!unitService.existsById(id)){
            return "redirect:/units";
        }
        Unit unit = unitService.findById(id);
        Short teacherId = unit.teacherId;
        Short subjectId = unit.subjectId;
        Short groupId = unit.groupId;

        model.addAttribute("teacherId", teacherId);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("groupId", groupId);
        return "units/units-edit";
    }

    @PostMapping("/units/{id}/edit")
    public String editPostUnit(@PathVariable(value = "id") Short id,
                               @RequestParam Short teacherId,
                               @RequestParam Short groupId,
                               @RequestParam Short subjectId, Model model){
        Unit unit = unitService.findById(id);
        unit.teacherId = teacherId;
        unit.groupId = groupId;
        unit.subjectId = subjectId;
        unitService.save(unit);
        return "redirect:/units";
    }

}
