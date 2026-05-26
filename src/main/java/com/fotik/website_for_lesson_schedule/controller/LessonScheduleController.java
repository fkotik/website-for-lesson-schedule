package com.fotik.website_for_lesson_schedule.controller;

import com.fotik.website_for_lesson_schedule.entity.LessonType;
import com.fotik.website_for_lesson_schedule.entity.Schedule;
import com.fotik.website_for_lesson_schedule.repository.*;
import com.fotik.website_for_lesson_schedule.service.LessonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/lesson-schedules")
public class LessonScheduleController {

    private final LessonScheduleService lessonScheduleService;
    private final DisciplineRepository disciplineRepository;
    private final TeacherRepository teacherRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final ClassroomRepository classroomRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public LessonScheduleController(LessonScheduleService lessonScheduleService,
                                    DisciplineRepository disciplineRepository,
                                    TeacherRepository teacherRepository,
                                    StudentGroupRepository studentGroupRepository,
                                    ClassroomRepository classroomRepository,
                                    TimeSlotRepository timeSlotRepository) {
        this.lessonScheduleService = lessonScheduleService;
        this.disciplineRepository = disciplineRepository;
        this.teacherRepository = teacherRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.classroomRepository = classroomRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping
    public String listSchedules(
            @RequestParam(value = "searchDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchDate,
            @RequestParam(value = "searchGroup", required = false) String searchGroup,
            Model model) {

        List<Schedule> schedules = lessonScheduleService.searchSchedules(searchDate, searchGroup);

        model.addAttribute("schedules", schedules);
        model.addAttribute("searchDate", searchDate);
        model.addAttribute("searchGroup", searchGroup);

        // Списки для выпадающих меню
        model.addAttribute("disciplines", disciplineRepository.findAll());
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("groups", studentGroupRepository.findAll());
        model.addAttribute("classrooms", classroomRepository.findAll());
        model.addAttribute("timeslots", timeSlotRepository.findAll());
        model.addAttribute("lessonTypes", LessonType.values()); // Enum для типа занятия

        if (!model.containsAttribute("newSchedule")) {
            model.addAttribute("newSchedule", new Schedule());
        }

        return "lesson-schedules"; // Имя HTML файла
    }

    @PostMapping
    public String addSchedule(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("timeslot") Integer timeslotId,
            @RequestParam("group") Integer groupId,
            @RequestParam("discipline") Integer disciplineId,
            @RequestParam("lessonType") LessonType lessonType,
            @RequestParam("teacher") Integer teacherId,
            @RequestParam("classroom") Integer classroomId,
            RedirectAttributes redirectAttributes) {
        try {
            Schedule schedule = new Schedule();
            schedule.setDate(date);
            schedule.setTimeslot(timeSlotRepository.findById(timeslotId)
                    .orElseThrow(() -> new IllegalArgumentException("Временной слот не найден")));
            schedule.setGroup(studentGroupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Группа не найдена")));
            schedule.setDiscipline(disciplineRepository.findById(disciplineId)
                    .orElseThrow(() -> new IllegalArgumentException("Дисциплина не найдена")));
            schedule.setLessonType(lessonType);
            schedule.setTeacher(teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new IllegalArgumentException("Преподаватель не найден")));
            schedule.setClassroom(classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new IllegalArgumentException("Аудитория не найдена")));

            lessonScheduleService.saveSchedule(schedule);
            redirectAttributes.addFlashAttribute("successMessage", "Занятие успешно добавлено в расписание!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Ошибка: Конфликт расписания! Преподаватель, группа или аудитория уже заняты в это время.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Произошла ошибка при добавлении занятия: " + e.getMessage());
        }
        return "redirect:/lesson-schedules";
    }

    @PostMapping("/{id}/update")
    public String updateSchedule(
            @PathVariable("id") Integer id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("timeslot") Integer timeslotId,
            @RequestParam("group") Integer groupId,
            @RequestParam("discipline") Integer disciplineId,
            @RequestParam("lessonType") LessonType lessonType,
            @RequestParam("teacher") Integer teacherId,
            @RequestParam("classroom") Integer classroomId,
            RedirectAttributes redirectAttributes) {
        try {
            Schedule schedule = new Schedule();
            schedule.setDate(date);
            schedule.setTimeslot(timeSlotRepository.findById(timeslotId).orElseThrow());
            schedule.setGroup(studentGroupRepository.findById(groupId).orElseThrow());
            schedule.setDiscipline(disciplineRepository.findById(disciplineId).orElseThrow());
            schedule.setLessonType(lessonType);
            schedule.setTeacher(teacherRepository.findById(teacherId).orElseThrow());
            schedule.setClassroom(classroomRepository.findById(classroomId).orElseThrow());

            lessonScheduleService.updateSchedule(id, schedule);
            redirectAttributes.addFlashAttribute("successMessage", "Данные расписания успешно обновлены!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка: Конфликт расписания!");
        }
        return "redirect:/lesson-schedules";
    }

    @PostMapping("/{id}/delete")
    public String deleteSchedule(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            lessonScheduleService.deleteSchedule(id);
            redirectAttributes.addFlashAttribute("successMessage", "Занятие успешно удалено из расписания!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Произошла ошибка при удалении.");
        }
        return "redirect:/lesson-schedules";
    }
}