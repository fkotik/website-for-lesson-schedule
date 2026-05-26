package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    @Query("SELECT s FROM Specialization s JOIN FETCH s.faculty f " +
            "WHERE LOWER(s.specializationName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(s.specializationCode) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Specialization> searchByNameOrCode(@Param("search") String search);

    @Query("SELECT s FROM Specialization s JOIN FETCH s.faculty")
    List<Specialization> findAllWithFaculty();
}