package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import com.fotik.website_for_lesson_schedule.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    @Autowired
    public StudentGroupService(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupRepository.findAll();
    }

    public List<StudentGroup> searchStudentGroups(String search) {
        if (search == null || search.isBlank()) {
            return getAllStudentGroups();
        }
        return studentGroupRepository.findByGroupNameContainingIgnoreCase(search);
    }

    public void saveStudentGroup(StudentGroup studentGroup) {
        validateGroup(studentGroup);

        if (studentGroupRepository.existsByGroupNameIgnoreCase(studentGroup.getGroupName())) {
            throw new IllegalArgumentException("Группа с названием '" + studentGroup.getGroupName() + "' уже существует!");
        }
        studentGroupRepository.save(studentGroup);
    }

    public void updateStudentGroup(Integer id, StudentGroup updatedGroup) {
        validateGroup(updatedGroup);

        StudentGroup existing = studentGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Группа не найдена"));

        if (studentGroupRepository.existsByGroupNameIgnoreCaseAndGroupIdNot(updatedGroup.getGroupName(), id)) {
            throw new IllegalArgumentException("Группа с названием '" + updatedGroup.getGroupName() + "' уже существует!");
        }

        existing.setGroupName(updatedGroup.getGroupName());
        existing.setCourseYear(updatedGroup.getCourseYear());
        existing.setSpecialization(updatedGroup.getSpecialization());

        studentGroupRepository.save(existing);
    }

    public void deleteStudentGroup(Integer id) {
        studentGroupRepository.deleteById(id);
    }

    private void validateGroup(StudentGroup group) {
        if (group.getCourseYear() == null || group.getCourseYear() < 1 || group.getCourseYear() > 6) {
            throw new IllegalArgumentException("Курс должен быть от 1 до 6!");
        }
    }
}