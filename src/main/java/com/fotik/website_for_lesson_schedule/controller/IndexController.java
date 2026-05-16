package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.repository.StudentGroup;
import com.fotik.website_for_lesson_schedule.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    public final GroupService groupService;

    public IndexController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<StudentGroup> groups = groupService.getGroups();

        model.addAttribute("groups", groups);

        return "index";
    }
}
