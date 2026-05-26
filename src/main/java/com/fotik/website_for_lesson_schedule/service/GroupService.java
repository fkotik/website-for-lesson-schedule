package com.fotik.website_for_lesson_schedule.service;

import com.fotik.website_for_lesson_schedule.entity.StudentGroup;
import com.fotik.website_for_lesson_schedule.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository studentGroupRepository;

    public GroupService(GroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    public List<StudentGroup> getGroups() {
        return studentGroupRepository.findAll();
    }

    public StudentGroup getGroupById(Integer id) {
        return studentGroupRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Группа с ID " + id + " не найдена"));
    }
}
