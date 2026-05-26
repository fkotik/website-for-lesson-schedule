package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {

    // Поиск по названию дисциплины (без учета регистра)
    List<Discipline> findByDisciplineNameContainingIgnoreCase(String disciplineName);
}