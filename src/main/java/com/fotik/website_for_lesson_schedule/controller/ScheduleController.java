package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.service.GroupService;
import com.fotik.website_for_lesson_schedule.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ScheduleController {

    private final GroupService groupService;
    private final ScheduleService scheduleService;

    public ScheduleController(GroupService groupService, ScheduleService scheduleService) {
        this.groupService = groupService;
        this.scheduleService = scheduleService;
    }


    @GetMapping("/schedule/{id}")
    public String schedule(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("group", groupService.getGroupById(id));
        model.addAttribute("schedulesByDate", scheduleService.getSchedulesGroupedByDate(id));
        return "schedule";
    }
}
