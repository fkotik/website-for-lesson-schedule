package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.Discipline;
import com.fotik.website_for_lesson_schedule.repository.DepartmentRepository;
import com.fotik.website_for_lesson_schedule.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;
    private final DepartmentRepository departmentRepository; // Для списка кафедр

    @Autowired
    public DisciplineController(DisciplineService disciplineService, DepartmentRepository departmentRepository) {
        this.disciplineService = disciplineService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public String listDisciplines(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Discipline> disciplines = disciplineService.searchDisciplines(search);

        model.addAttribute("disciplines", disciplines);
        model.addAttribute("departments", departmentRepository.findAll()); // Список кафедр для <select>
        model.addAttribute("search", search);

        if (!model.containsAttribute("newDiscipline")) {
            model.addAttribute("newDiscipline", new Discipline());
        }

        return "disciplines"; // Имя HTML файла
    }

    @PostMapping
    public String addDiscipline(@ModelAttribute("newDiscipline") Discipline discipline, RedirectAttributes redirectAttributes) {
        try {
            disciplineService.saveDiscipline(discipline);
            redirectAttributes.addFlashAttribute("successMessage", "Дисциплина успешно добавлена!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Невозможно сохранить данные. Проверьте обязательные поля.");
        }
        return "redirect:/disciplines";
    }

    @PostMapping("/{id}/update")
    public String updateDiscipline(@PathVariable("id") Integer id,
                                   @ModelAttribute Discipline discipline,
                                   RedirectAttributes redirectAttributes) {
        try {
            disciplineService.updateDiscipline(id, discipline);
            redirectAttributes.addFlashAttribute("successMessage", "Данные дисциплины успешно обновлены!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Нарушение ограничений базы данных.");
        }
        return "redirect:/disciplines";
    }

    @PostMapping("/{id}/delete")
    public String deleteDiscipline(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            disciplineService.deleteDiscipline(id);
            redirectAttributes.addFlashAttribute("successMessage", "Дисциплина успешно удалена!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка удаления: Эта дисциплина уже используется в расписании!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла системная ошибка при удалении.");
        }
        return "redirect:/disciplines";
    }
}