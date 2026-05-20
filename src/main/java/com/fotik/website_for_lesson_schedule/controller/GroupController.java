package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import com.fotik.website_for_lesson_schedule.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping("/group/{id}")
    public String group(@PathVariable("id") Integer id, Model model) {
        StudentGroup group = groupService.getGroupById(id);
        model.addAttribute("group", group);
        return "group";
    }
}
