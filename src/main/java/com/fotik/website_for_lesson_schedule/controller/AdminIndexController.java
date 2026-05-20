package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminIndexController {

    @GetMapping("/admin")
    public String adminIndex() {
        return "adminIndex";
    }
}
