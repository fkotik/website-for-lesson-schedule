package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Schedule;
import com.fotik.website_for_lesson_schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getSchedulesByGroupId(Integer groupId) {
        return scheduleRepository.findByGroupIdWithDetails(groupId);
    }

    // Группировка занятий по датам (сортировка по дате)
    public Map<LocalDate, List<Schedule>> getSchedulesGroupedByDate(Integer groupId) {
        return scheduleRepository.findByGroupIdWithDetails(groupId)
                .stream()
                .collect(Collectors.groupingBy(
                        Schedule::getDate,
                        TreeMap::new,
                        Collectors.toList()
                ));
    }
}
