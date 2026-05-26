package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Faculty;
import com.fotik.website_for_lesson_schedule.repository.FacultyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultyService {


    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAllOrdered();
    }

    public List<Faculty> searchByName(String name) {
        if (name == null || name.isBlank()) {
            return facultyRepository.findAllOrdered();
        }
        return facultyRepository.findByFacultyNameContainingIgnoreCase(name.trim());
    }

    public Faculty getFacultyById(Integer id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Факультет с ID " + id + " не найден"));
    }

    @Transactional
    public void createFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
    }

    @Transactional
    public void updateFaculty(Integer id, Faculty updatedFaculty) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Факультет с ID " + id + " не найден"));

        faculty.setFacultyName(updatedFaculty.getFacultyName());
        faculty.setDeanName(updatedFaculty.getDeanName());

        facultyRepository.save(faculty);
    }

    @Transactional
    public void deleteFaculty(Integer id) {
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException("Факультет с ID " + id + " не найден");
        }
        facultyRepository.deleteById(id);
    }


}