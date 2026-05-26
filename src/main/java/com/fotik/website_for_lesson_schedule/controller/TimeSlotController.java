package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.TimeSlot;
import com.fotik.website_for_lesson_schedule.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    public String listTimeSlots(@RequestParam(value = "search", required = false) Integer search, Model model) {
        List<TimeSlot> timeslots = timeSlotService.searchTimeSlots(search);

        model.addAttribute("timeslots", timeslots);
        model.addAttribute("search", search);
        if (!model.containsAttribute("newTimeSlot")) {
            model.addAttribute("newTimeSlot", new TimeSlot());
        }

        return "timeslots";
    }

    @PostMapping
    public String addTimeSlot(@ModelAttribute("newTimeSlot") TimeSlot timeSlot, RedirectAttributes redirectAttributes) {
        try {
            timeSlotService.saveTimeSlot(timeSlot);
            redirectAttributes.addFlashAttribute("successMessage", "Таймслот успешно добавлен!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Неверный формат времени или нарушение ограничений базы данных.");
        }
        return "redirect:/timeslots";
    }

    @PostMapping("/{id}/update")
    public String updateTimeSlot(@PathVariable("id") Integer id,
                                 @ModelAttribute TimeSlot timeSlot,
                                 RedirectAttributes redirectAttributes) {
        try {
            timeSlotService.updateTimeSlot(id, timeSlot);
            redirectAttributes.addFlashAttribute("successMessage", "Таймслот успешно обновлен!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка БД: Неверный формат времени или нарушение ограничений базы данных.");
        }
        return "redirect:/timeslots";
    }

    @PostMapping("/{id}/delete")
    public String deleteTimeSlot(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            timeSlotService.deleteTimeSlot(id);
            redirectAttributes.addFlashAttribute("successMessage", "Таймслот успешно удален!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка удаления: С этим таймслотом связаны занятия в расписании!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла системная ошибка при удалении.");
        }
        return "redirect:/timeslots";
    }
}