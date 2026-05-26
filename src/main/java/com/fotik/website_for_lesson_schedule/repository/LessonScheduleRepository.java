package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LessonScheduleRepository extends JpaRepository<Schedule, Integer> {

    // Поиск только по дате
    List<Schedule> findByDate(LocalDate date);

    // Поиск только по названию группы
    List<Schedule> findByGroup_GroupNameContainingIgnoreCase(String groupName);

    // Поиск одновременно и по дате, и по названию группы
    List<Schedule> findByDateAndGroup_GroupNameContainingIgnoreCase(LocalDate date, String groupName);
}