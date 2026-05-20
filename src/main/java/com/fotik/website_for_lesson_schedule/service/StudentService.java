package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Student;
import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import com.fotik.website_for_lesson_schedule.repository.StudentGroupRepository;
import com.fotik.website_for_lesson_schedule.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;

    public StudentService(StudentRepository studentRepository,
                          StudentGroupRepository studentGroupRepository) {
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllWithGroup();
    }

    // Поиск по фамилии — если строка пустая, возвращает всех
    public List<Student> searchByLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            return studentRepository.findAllWithGroup();
        }
        return studentRepository.findByLastNameContainingIgnoreCase(lastName.trim());
    }

    public Student getStudentById(Integer id) {
        return studentRepository.findByIdWithGroup(id)
                .orElseThrow(() -> new RuntimeException("Студент с ID " + id + " не найден"));
    }

    public List<StudentGroup> getAllGroups() {
        return studentGroupRepository.findAll();
    }

    @Transactional
    public void createStudent(Student student, Integer groupId) {
        StudentGroup group = studentGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Группа с ID " + groupId + " не найдена"));

        student.setGroup(group);
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Integer studentId, Student updatedStudent, Integer groupId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Студент с ID " + studentId + " не найден"));

        StudentGroup group = studentGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Группа с ID " + groupId + " не найдена"));

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setMiddleName(updatedStudent.getMiddleName());
        student.setDateOfBirth(updatedStudent.getDateOfBirth());
        student.setEmail(updatedStudent.getEmail());
        student.setPhone(updatedStudent.getPhone());
        student.setGroup(group);

        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new RuntimeException("Студент с ID " + studentId + " не найден");
        }
        studentRepository.deleteById(studentId);
    }
}