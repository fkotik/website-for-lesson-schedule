package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    // Поиск по фамилии, имени или email (без учета регистра)
    List<Teacher> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String lastName, String firstName, String email);

    // Проверка уникальности email при создании
    boolean existsByEmailIgnoreCase(String email);

    // Проверка уникальности email при редактировании (исключая текущего преподавателя)
    boolean existsByEmailIgnoreCaseAndTeacherIdNot(String email, Integer teacherId);
}