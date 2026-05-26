package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Department;
import com.fotik.website_for_lesson_schedule.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Department> searchDepartments(String search) {
        if (search == null || search.isBlank()) {
            return getAllDepartments();
        }
        return departmentRepository.findByDepartmentNameContainingIgnoreCase(search);
    }

    public void saveDepartment(Department department) {
        if (departmentRepository.existsByDepartmentNameIgnoreCase(department.getDepartmentName())) {
            throw new IllegalArgumentException("Кафедра с названием '" + department.getDepartmentName() + "' уже существует!");
        }
        departmentRepository.save(department);
    }

    public void updateDepartment(Integer id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Кафедра не найдена"));

        if (departmentRepository.existsByDepartmentNameIgnoreCaseAndDepartmentIdNot(
                updatedDepartment.getDepartmentName(), id)) {
            throw new IllegalArgumentException("Кафедра с названием '" + updatedDepartment.getDepartmentName() + "' уже существует!");
        }

        existing.setDepartmentName(updatedDepartment.getDepartmentName());
        existing.setFaculty(updatedDepartment.getFaculty());

        // Зав. кафедрой может быть null, поэтому обновляем напрямую
        existing.setHeadTeacher(updatedDepartment.getHeadTeacher());

        departmentRepository.save(existing);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }
}