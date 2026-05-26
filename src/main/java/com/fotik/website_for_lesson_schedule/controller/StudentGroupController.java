package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import com.fotik.website_for_lesson_schedule.repository.SpecializationRepository;
import com.fotik.website_for_lesson_schedule.service.StudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student-groups")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;
    private final SpecializationRepository specializationRepository; // Для списка специальностей

    @Autowired
    public StudentGroupController(StudentGroupService studentGroupService,
                                  SpecializationRepository specializationRepository) {
        this.studentGroupService = studentGroupService;
        this.specializationRepository = specializationRepository;
    }

    @GetMapping
    public String listStudentGroups(@RequestParam(value = "search", required = false) String search, Model model) {
        List<StudentGroup> studentGroups = studentGroupService.searchStudentGroups(search);

        model.addAttribute("studentGroups", studentGroups);
        model.addAttribute("specializations", specializationRepository.findAll()); // Список для <select>
        model.addAttribute("search", search);

        if (!model.containsAttribute("newStudentGroup")) {
            model.addAttribute("newStudentGroup", new StudentGroup());
        }

        return "student-groups"; // Имя HTML файла
    }

    @PostMapping
    public String addStudentGroup(@ModelAttribute("newStudentGroup") StudentGroup studentGroup, RedirectAttributes redirectAttributes) {
        try {
            studentGroupService.saveStudentGroup(studentGroup);
            redirectAttributes.addFlashAttribute("successMessage", "Группа успешно добавлена!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Проверьте правильность введенных данных.");
        }
        return "redirect:/student-groups";
    }

    @PostMapping("/{id}/update")
    public String updateStudentGroup(@PathVariable("id") Integer id,
                                     @ModelAttribute StudentGroup studentGroup,
                                     RedirectAttributes redirectAttributes) {
        try {
            studentGroupService.updateStudentGroup(id, studentGroup);
            redirectAttributes.addFlashAttribute("successMessage", "Данные группы успешно обновлены!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Нарушение ограничений базы данных.");
        }
        return "redirect:/student-groups";
    }

    @PostMapping("/{id}/delete")
    public String deleteStudentGroup(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            studentGroupService.deleteStudentGroup(id);
            redirectAttributes.addFlashAttribute("successMessage", "Группа успешно удалена!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка удаления: В этой группе уже есть студенты или занятия!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла системная ошибка при удалении.");
        }
        return "redirect:/student-groups";
    }
}