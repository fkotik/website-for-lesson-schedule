package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {

    // Поиск по названию группы (без учета регистра)
    List<StudentGroup> findByGroupNameContainingIgnoreCase(String groupName);

    // Проверка уникальности названия при создании
    boolean existsByGroupNameIgnoreCase(String groupName);

    // Проверка уникальности названия при редактировании (исключая текущую)
    boolean existsByGroupNameIgnoreCaseAndGroupIdNot(String groupName, Integer groupId);
}