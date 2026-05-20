package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Student;
import com.fotik.website_for_lesson_schedule.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Страница со всеми студентами + поиск по фамилии
    @GetMapping
    public String studentsPage(@RequestParam(value = "search", required = false) String search,
                               Model model) {
        model.addAttribute("students", studentService.searchByLastName(search));
        model.addAttribute("groups", studentService.getAllGroups());
        model.addAttribute("newStudent", new Student());
        model.addAttribute("search", search);

        return "students";
    }

    // Добавление студента
    @PostMapping
    public String createStudent(@ModelAttribute("newStudent") Student student,
                                @RequestParam("groupId") Integer groupId,
                                RedirectAttributes redirectAttributes) {
        try {
            studentService.createStudent(student, groupId);
            redirectAttributes.addFlashAttribute("successMessage", "Студент успешно добавлен");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении: " + e.getMessage());
        }

        return "redirect:/students";
    }

    // Редактирование студента
    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable("id") Integer id,
                                @ModelAttribute Student student,
                                @RequestParam("groupId") Integer groupId,
                                RedirectAttributes redirectAttributes) {
        try {
            studentService.updateStudent(id, student, groupId);
            redirectAttributes.addFlashAttribute("successMessage", "Данные студента обновлены");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при обновлении: " + e.getMessage());
        }

        return "redirect:/students";
    }

    // Удаление студента
    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable("id") Integer id,
                                RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Студент удалён");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении: " + e.getMessage());
        }

        return "redirect:/students";
    }
}