package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    // Поиск по номеру пары
    List<TimeSlot> findBySlotNumber(Integer slotNumber);

    // Проверка существования пары с таким номером (чтобы избежать дубликатов)
    boolean existsBySlotNumber(Integer slotNumber);
    boolean existsBySlotNumberAndTimeslotIdNot(Integer slotNumber, Integer timeslotId);
}