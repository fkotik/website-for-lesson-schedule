package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, Integer> {

    @Query("""
                SELECT DISTINCT g FROM StudentGroup g
                LEFT JOIN FETCH g.specialization s
                LEFT JOIN FETCH s.faculty f
                LEFT JOIN FETCH f.departments
                LEFT JOIN FETCH g.students
                WHERE g.id = :id
            """)
    Optional<StudentGroup> findByIdWithDetails(@Param("id") Integer id);
}
