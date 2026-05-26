package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Faculty;
import com.fotik.website_for_lesson_schedule.entity.Specialization;
import com.fotik.website_for_lesson_schedule.repository.FacultyRepository;
import com.fotik.website_for_lesson_schedule.service.SpecializationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;
    private final FacultyRepository facultyRepository;

    public SpecializationController(SpecializationService specializationService,
                                    FacultyRepository facultyRepository) {
        this.specializationService = specializationService;
        this.facultyRepository = facultyRepository;
    }

    @GetMapping
    public String list(@RequestParam(value = "search", required = false) String search,
                       Model model) {
        List<Specialization> specializations = specializationService.search(search);
        List<Faculty> faculties = facultyRepository.findAll();

        model.addAttribute("specializations", specializations);
        model.addAttribute("faculties", faculties);
        model.addAttribute("search", search);
        model.addAttribute("newSpecialization", new Specialization());
        return "specializations";
    }

    @PostMapping
    public String add(@ModelAttribute("newSpecialization") Specialization specialization,
                      @RequestParam("facultyId") Integer facultyId,
                      RedirectAttributes redirectAttributes) {
        try {
            specializationService.save(specialization, facultyId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Специализация «" + specialization.getSpecializationName() + "» успешно добавлена.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при добавлении: " + e.getMessage());
        }
        return "redirect:/specializations";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id,
                         @RequestParam("specializationName") String name,
                         @RequestParam("specializationCode") String code,
                         @RequestParam("facultyId") Integer facultyId,
                         RedirectAttributes redirectAttributes) {
        try {
            specializationService.update(id, name, code, facultyId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Специализация успешно обновлена.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при обновлении: " + e.getMessage());
        }
        return "redirect:/specializations";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        try {
            specializationService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Специализация успешно удалена.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка при удалении: " + e.getMessage());
        }
        return "redirect:/specializations";
    }
}