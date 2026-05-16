package com.fotik.website_for_lesson_schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeachersController {
    @GetMapping("/teachers")
    public String teachers() {
        return "teachers";
    }
}
