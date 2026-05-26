package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Schedule;
import com.fotik.website_for_lesson_schedule.repository.LessonScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonScheduleService {

    private final LessonScheduleRepository lessonScheduleRepository;

    @Autowired
    public LessonScheduleService(LessonScheduleRepository lessonScheduleRepository) {
        this.lessonScheduleRepository = lessonScheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return lessonScheduleRepository.findAll();
    }

    public List<Schedule> searchSchedules(LocalDate date, String groupName) {
        boolean hasDate = (date != null);
        boolean hasGroup = (groupName != null && !groupName.isBlank());

        if (hasDate && hasGroup) {
            // Ищем и по дате, и по группе
            return lessonScheduleRepository.findByDateAndGroup_GroupNameContainingIgnoreCase(date, groupName);
        } else if (hasDate) {
            // Ищем только по дате
            return lessonScheduleRepository.findByDate(date);
        } else if (hasGroup) {
            // Ищем только по группе
            return lessonScheduleRepository.findByGroup_GroupNameContainingIgnoreCase(groupName);
        } else {
            // Ничего не введено — возвращаем всё
            return getAllSchedules();
        }
    }

    public void saveSchedule(Schedule schedule) {
        lessonScheduleRepository.save(schedule);
    }

    public void updateSchedule(Integer id, Schedule updatedSchedule) {
        Schedule existing = lessonScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Запись расписания не найдена"));

        existing.setDiscipline(updatedSchedule.getDiscipline());
        existing.setTeacher(updatedSchedule.getTeacher());
        existing.setGroup(updatedSchedule.getGroup());
        existing.setClassroom(updatedSchedule.getClassroom());
        existing.setTimeslot(updatedSchedule.getTimeslot());
        existing.setLessonType(updatedSchedule.getLessonType());
        existing.setDate(updatedSchedule.getDate());

        lessonScheduleRepository.save(existing);
    }

    public void deleteSchedule(Integer id) {
        lessonScheduleRepository.deleteById(id);
    }
}