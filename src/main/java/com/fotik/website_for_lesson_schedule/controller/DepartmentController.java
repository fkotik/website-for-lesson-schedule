package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Department;
import com.fotik.website_for_lesson_schedule.repository.FacultyRepository;
import com.fotik.website_for_lesson_schedule.repository.TeacherRepository;
import com.fotik.website_for_lesson_schedule.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final FacultyRepository facultyRepository; // Для списка факультетов
    private final TeacherRepository teacherRepository; // Для списка преподавателей (зав. кафедрой)

    @Autowired
    public DepartmentController(DepartmentService departmentService,
                                FacultyRepository facultyRepository,
                                TeacherRepository teacherRepository) {
        this.departmentService = departmentService;
        this.facultyRepository = facultyRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public String listDepartments(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Department> departments = departmentService.searchDepartments(search);

        model.addAttribute("departments", departments);
        model.addAttribute("faculties", facultyRepository.findAll()); // Передаем факультеты для <select>
        model.addAttribute("teachers", teacherRepository.findAll()); // Передаем преподавателей для <select>
        model.addAttribute("search", search);

        if (!model.containsAttribute("newDepartment")) {
            model.addAttribute("newDepartment", new Department());
        }

        return "departments"; // Имя HTML файла
    }

    @PostMapping
    public String addDepartment(@ModelAttribute("newDepartment") Department department, RedirectAttributes redirectAttributes) {
        try {
            departmentService.saveDepartment(department);
            redirectAttributes.addFlashAttribute("successMessage", "Кафедра успешно добавлена!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Невозможно сохранить кафедру. Проверьте правильность данных.");
        }
        return "redirect:/departments";
    }

    @PostMapping("/{id}/update")
    public String updateDepartment(@PathVariable("id") Integer id,
                                   @ModelAttribute Department department,
                                   RedirectAttributes redirectAttributes) {
        try {
            departmentService.updateDepartment(id, department);
            redirectAttributes.addFlashAttribute("successMessage", "Данные кафедры успешно обновлены!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Нарушение ограничений базы данных.");
        }
        return "redirect:/departments";
    }

    @PostMapping("/{id}/delete")
    public String deleteDepartment(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Кафедра успешно удалена!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка удаления: К этой кафедре уже привязаны преподаватели или дисциплины!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла системная ошибка при удалении.");
        }
        return "redirect:/departments";
    }
}