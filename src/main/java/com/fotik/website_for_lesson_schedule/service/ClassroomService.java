package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.Classroom;
import com.fotik.website_for_lesson_schedule.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public List<Classroom> searchClassrooms(String search) {
        if (search == null || search.isBlank()) {
            return getAllClassrooms();
        }
        return classroomRepository.findByRoomNumberContainingIgnoreCaseOrBuildingContainingIgnoreCase(search, search);
    }

    public void saveClassroom(Classroom classroom) {
        if (classroomRepository.existsByRoomNumberAndBuilding(classroom.getRoomNumber(), classroom.getBuilding())) {
            throw new IllegalArgumentException("Аудитория №" + classroom.getRoomNumber() + " в корпусе '" + classroom.getBuilding() + "' уже существует!");
        }
        classroomRepository.save(classroom);
    }

    public void updateClassroom(Integer id, Classroom updatedClassroom) {
        Classroom existing = classroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Аудитория не найдена"));

        if (classroomRepository.existsByRoomNumberAndBuildingAndClassroomIdNot(
                updatedClassroom.getRoomNumber(), updatedClassroom.getBuilding(), id)) {
            throw new IllegalArgumentException("Аудитория №" + updatedClassroom.getRoomNumber() + " в корпусе '" + updatedClassroom.getBuilding() + "' уже существует!");
        }

        existing.setRoomNumber(updatedClassroom.getRoomNumber());
        existing.setBuilding(updatedClassroom.getBuilding());
        existing.setCapacity(updatedClassroom.getCapacity());
        existing.setClassroomType(updatedClassroom.getClassroomType());

        classroomRepository.save(existing);
    }

    public void deleteClassroom(Integer id) {
        classroomRepository.deleteById(id);
    }
}