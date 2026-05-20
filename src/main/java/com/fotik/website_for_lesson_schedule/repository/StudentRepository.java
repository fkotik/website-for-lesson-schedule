package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("""
                SELECT s FROM Student s
                LEFT JOIN FETCH s.group g
                ORDER BY s.lastName ASC, s.firstName ASC
            """)
    List<Student> findAllWithGroup();

    @Query("""
                SELECT s FROM Student s
                LEFT JOIN FETCH s.group g
                WHERE s.studentId = :id
            """)
    Optional<Student> findByIdWithGroup(Integer id);

    @Query("""
                SELECT s FROM Student s
                LEFT JOIN FETCH s.group g
                WHERE LOWER(s.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))
                ORDER BY s.lastName ASC, s.firstName ASC
            """)
    List<Student> findByLastNameContainingIgnoreCase(@Param("lastName") String lastName);
}