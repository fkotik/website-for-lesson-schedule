package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Faculty;
import com.fotik.website_for_lesson_schedule.entity.Specialization;
import com.fotik.website_for_lesson_schedule.repository.FacultyRepository;
import com.fotik.website_for_lesson_schedule.repository.SpecializationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final FacultyRepository facultyRepository;

    public SpecializationService(SpecializationRepository specializationRepository,
                                 FacultyRepository facultyRepository) {
        this.specializationRepository = specializationRepository;
        this.facultyRepository = facultyRepository;
    }

    @Transactional(readOnly = true)
    public List<Specialization> findAll() {
        return specializationRepository.findAllWithFaculty();
    }

    @Transactional(readOnly = true)
    public List<Specialization> search(String query) {
        if (query == null || query.isBlank()) {
            return specializationRepository.findAllWithFaculty();
        }
        return specializationRepository.searchByNameOrCode(query.trim());
    }

    @Transactional(readOnly = true)
    public Specialization findById(Integer id) {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Специализация не найдена: " + id));
    }

    public void save(Specialization specialization, Integer facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Факультет не найден: " + facultyId));
        specialization.setFaculty(faculty);
        specializationRepository.save(specialization);
    }

    public void update(Integer id, String name, String code, Integer facultyId) {
        Specialization existing = findById(id);
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Факультет не найден: " + facultyId));
        existing.setSpecializationName(name);
        existing.setSpecializationCode(code);
        existing.setFaculty(faculty);
        specializationRepository.save(existing);
    }

    public void delete(Integer id) {
        specializationRepository.deleteById(id);
    }
}