package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Teacher;
import com.fotik.website_for_lesson_schedule.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Teacher> searchTeachers(String search) {
        if (search == null || search.isBlank()) {
            return getAllTeachers();
        }
        return teacherRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                search, search, search);
    }

    public void saveTeacher(Teacher teacher) {
        if (teacherRepository.existsByEmailIgnoreCase(teacher.getEmail())) {
            throw new IllegalArgumentException("Преподаватель с email '" + teacher.getEmail() + "' уже существует!");
        }
        teacherRepository.save(teacher);
    }

    public void updateTeacher(Integer id, Teacher updatedTeacher) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Преподаватель не найден"));

        if (teacherRepository.existsByEmailIgnoreCaseAndTeacherIdNot(updatedTeacher.getEmail(), id)) {
            throw new IllegalArgumentException("Преподаватель с email '" + updatedTeacher.getEmail() + "' уже существует!");
        }

        existing.setFirstName(updatedTeacher.getFirstName());
        existing.setLastName(updatedTeacher.getLastName());
        existing.setMiddleName(updatedTeacher.getMiddleName());
        existing.setAcademicDegree(updatedTeacher.getAcademicDegree());
        existing.setEmail(updatedTeacher.getEmail());
        existing.setPhone(updatedTeacher.getPhone());
        existing.setDepartment(updatedTeacher.getDepartment());

        teacherRepository.save(existing);
    }

    public void deleteTeacher(Integer id) {
        teacherRepository.deleteById(id);
    }
}