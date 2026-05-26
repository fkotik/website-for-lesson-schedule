package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Faculty;
import com.fotik.website_for_lesson_schedule.service.FacultyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public String facultiesPage(@RequestParam(value = "search", required = false) String search,
                                Model model) {
        model.addAttribute("faculties", facultyService.searchByName(search));
        model.addAttribute("newFaculty", new Faculty());
        model.addAttribute("search", search);
        return "faculties";
    }

    @PostMapping
    public String createFaculty(@ModelAttribute("newFaculty") Faculty faculty,
                                RedirectAttributes redirectAttributes) {
        try {
            facultyService.createFaculty(faculty);
            redirectAttributes.addFlashAttribute("successMessage", "Факультет успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/faculties";
    }

    @PostMapping("/{id}/update")
    public String updateFaculty(@PathVariable("id") Integer id,
                                @ModelAttribute Faculty faculty,
                                RedirectAttributes redirectAttributes) {
        try {
            facultyService.updateFaculty(id, faculty);
            redirectAttributes.addFlashAttribute("successMessage", "Факультет успешно обновлён");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/faculties";
    }

    @PostMapping("/{id}/delete")
    public String deleteFaculty(@PathVariable("id") Integer id,
                                RedirectAttributes redirectAttributes) {
        try {
            facultyService.deleteFaculty(id);
            redirectAttributes.addFlashAttribute("successMessage", "Факультет удалён");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/faculties";
    }
}