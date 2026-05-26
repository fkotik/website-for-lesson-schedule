package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Classroom;
import com.fotik.website_for_lesson_schedule.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public String listClassrooms(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Classroom> classrooms = classroomService.searchClassrooms(search);

        model.addAttribute("classrooms", classrooms);
        model.addAttribute("search", search);
        if (!model.containsAttribute("newClassroom")) {
            model.addAttribute("newClassroom", new Classroom());
        }

        return "classrooms"; // Имя HTML файла
    }

    @PostMapping
    public String addClassroom(@ModelAttribute("newClassroom") Classroom classroom, RedirectAttributes redirectAttributes) {
        try {
            classroomService.saveClassroom(classroom);
            redirectAttributes.addFlashAttribute("successMessage", "Аудитория успешно добавлена!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Нарушение ограничений (проверьте уникальность номера и корпуса).");
        }
        return "redirect:/classrooms";
    }

    @PostMapping("/{id}/update")
    public String updateClassroom(@PathVariable("id") Integer id,
                                  @ModelAttribute Classroom classroom,
                                  RedirectAttributes redirectAttributes) {
        try {
            classroomService.updateClassroom(id, classroom);
            redirectAttributes.addFlashAttribute("successMessage", "Данные аудитории успешно обновлены!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Нарушение ограничений базы данных.");
        }
        return "redirect:/classrooms";
    }

    @PostMapping("/{id}/delete")
    public String deleteClassroom(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            classroomService.deleteClassroom(id);
            redirectAttributes.addFlashAttribute("successMessage", "Аудитория успешно удалена!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка удаления: В этой аудитории уже назначены занятия (связь с таблицей расписания)!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла системная ошибка при удалении.");
        }
        return "redirect:/classrooms";
    }
}