package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    @Query("""
        SELECT f FROM Faculty f
        ORDER BY f.facultyName ASC
    """)
    List<Faculty> findAllOrdered();

    @Query("""
        SELECT f FROM Faculty f
        WHERE LOWER(f.facultyName) LIKE LOWER(CONCAT('%', :name, '%'))
        ORDER BY f.facultyName ASC
    """)
    List<Faculty> findByFacultyNameContainingIgnoreCase(@Param("name") String name);
}