package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Teacher;
import com.fotik.website_for_lesson_schedule.repository.DepartmentRepository;
import com.fotik.website_for_lesson_schedule.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final DepartmentRepository departmentRepository; // Для выпадающего списка кафедр

    @Autowired
    public TeacherController(TeacherService teacherService, DepartmentRepository departmentRepository) {
        this.teacherService = teacherService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public String listTeachers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Teacher> teachers = teacherService.searchTeachers(search);

        model.addAttribute("teachers", teachers);
        model.addAttribute("departments", departmentRepository.findAll()); // Список кафедр для <select>
        model.addAttribute("search", search);

        if (!model.containsAttribute("newTeacher")) {
            model.addAttribute("newTeacher", new Teacher());
        }

        return "teachers"; // Имя HTML файла
    }

    @PostMapping
    public String addTeacher(@ModelAttribute("newTeacher") Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            teacherService.saveTeacher(teacher);
            redirectAttributes.addFlashAttribute("successMessage", "Преподаватель успешно добавлен!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Невозможно сохранить данные. Проверьте обязательные поля.");
        }
        return "redirect:/teachers";
    }

    @PostMapping("/{id}/update")
    public String updateTeacher(@PathVariable("id") Integer id,
                                @ModelAttribute Teacher teacher,
                                RedirectAttributes redirectAttributes) {
        try {
            teacherService.updateTeacher(id, teacher);
            redirectAttributes.addFlashAttribute("successMessage", "Данные преподавателя успешно обновлены!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Нарушение ограничений базы данных.");
        }
        return "redirect:/teachers";
    }

    @PostMapping("/{id}/delete")
    public String deleteTeacher(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            teacherService.deleteTeacher(id);
            redirectAttributes.addFlashAttribute("successMessage", "Преподаватель успешно удален!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка удаления: Этот преподаватель уже назначен на занятия в расписании или является зав. кафедрой!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла системная ошибка при удалении.");
        }
        return "redirect:/teachers";
    }
}