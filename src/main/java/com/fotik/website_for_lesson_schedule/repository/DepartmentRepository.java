package com.fotik.website_for_lesson_schedule.repository;

import com.fotik.website_for_lesson_schedule.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // Поиск по названию кафедры (без учета регистра)
    List<Department> findByDepartmentNameContainingIgnoreCase(String departmentName);

    // Проверка уникальности названия при добавлении
    boolean existsByDepartmentNameIgnoreCase(String departmentName);

    // Проверка уникальности названия при редактировании (исключая текущую)
    boolean existsByDepartmentNameIgnoreCaseAndDepartmentIdNot(String departmentName, Integer departmentId);
}