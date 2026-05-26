package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Discipline;
import com.fotik.website_for_lesson_schedule.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    public List<Discipline> searchDisciplines(String search) {
        if (search == null || search.isBlank()) {
            return getAllDisciplines();
        }
        return disciplineRepository.findByDisciplineNameContainingIgnoreCase(search);
    }

    public void saveDiscipline(Discipline discipline) {
        validateDiscipline(discipline);
        disciplineRepository.save(discipline);
    }

    public void updateDiscipline(Integer id, Discipline updatedDiscipline) {
        validateDiscipline(updatedDiscipline);

        Discipline existing = disciplineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Дисциплина не найдена"));

        existing.setDisciplineName(updatedDiscipline.getDisciplineName());
        existing.setCredits(updatedDiscipline.getCredits());
        existing.setHoursTotal(updatedDiscipline.getHoursTotal());
        existing.setDepartment(updatedDiscipline.getDepartment());

        disciplineRepository.save(existing);
    }

    public void deleteDiscipline(Integer id) {
        disciplineRepository.deleteById(id);
    }

    // Вспомогательный метод для проверки числовых значений
    private void validateDiscipline(Discipline discipline) {
        if (discipline.getCredits() == null || discipline.getCredits() < 0) {
            throw new IllegalArgumentException("Количество зачетных единиц (кредитов) не может быть отрицательным!");
        }
        if (discipline.getHoursTotal() == null || discipline.getHoursTotal() <= 0) {
            throw new IllegalArgumentException("Общее количество часов должно быть больше нуля!");
        }
    }
}