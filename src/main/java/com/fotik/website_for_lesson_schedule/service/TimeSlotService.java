package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.TimeSlot;
import com.fotik.website_for_lesson_schedule.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public List<TimeSlot> searchTimeSlots(Integer search) {
        if (search == null) {
            return getAllTimeSlots();
        }
        return timeSlotRepository.findBySlotNumber(search);
    }

    public void saveTimeSlot(TimeSlot timeSlot) {
        validateTimeLogic(timeSlot); // Проверяем логику времени

        if (timeSlotRepository.existsBySlotNumber(timeSlot.getSlotNumber())) {
            throw new IllegalArgumentException("Таймслот с таким номером уже существует!");
        }
        timeSlotRepository.save(timeSlot);
    }

    public void updateTimeSlot(Integer id, TimeSlot updatedTimeSlot) {
        validateTimeLogic(updatedTimeSlot); // Проверяем логику времени

        TimeSlot existing = timeSlotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Таймслот не найден"));

        if (timeSlotRepository.existsBySlotNumberAndTimeslotIdNot(updatedTimeSlot.getSlotNumber(), id)) {
            throw new IllegalArgumentException("Таймслот с таким номером уже существует!");
        }

        existing.setSlotNumber(updatedTimeSlot.getSlotNumber());
        existing.setStartTime(updatedTimeSlot.getStartTime());
        existing.setEndTime(updatedTimeSlot.getEndTime());

        timeSlotRepository.save(existing);
    }

    public void deleteTimeSlot(Integer id) {
        timeSlotRepository.deleteById(id);
    }

    // Вспомогательный метод для проверки правильности времени
    private void validateTimeLogic(TimeSlot timeSlot) {
        if (timeSlot.getStartTime() != null && timeSlot.getEndTime() != null) {
            if (!timeSlot.getStartTime().isBefore(timeSlot.getEndTime())) {
                throw new IllegalArgumentException("Ошибка: Время начала пары должно быть раньше времени окончания!");
            }
        }
    }
}