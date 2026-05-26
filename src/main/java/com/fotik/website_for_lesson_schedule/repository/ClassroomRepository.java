package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    // Поиск по номеру аудитории или названию корпуса (без учета регистра)
    List<Classroom> findByRoomNumberContainingIgnoreCaseOrBuildingContainingIgnoreCase(String roomNumber, String building);

    // Проверка уникальности (Номер + Корпус) для создания
    boolean existsByRoomNumberAndBuilding(String roomNumber, String building);

    // Проверка уникальности (Номер + Корпус) для обновления (исключая текущую аудиторию)
    boolean existsByRoomNumberAndBuildingAndClassroomIdNot(String roomNumber, String building, Integer classroomId);
}