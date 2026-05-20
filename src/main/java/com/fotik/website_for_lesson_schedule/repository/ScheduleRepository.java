package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("""
                SELECT s FROM Schedule s
                LEFT JOIN FETCH s.discipline
                LEFT JOIN FETCH s.teacher
                LEFT JOIN FETCH s.classroom
                LEFT JOIN FETCH s.timeslot ts
                LEFT JOIN FETCH s.group g
                WHERE g.groupId = :groupId
                ORDER BY s.date ASC, ts.slotNumber ASC
            """)
    List<Schedule> findByGroupIdWithDetails(@Param("groupId") Integer groupId);
}
