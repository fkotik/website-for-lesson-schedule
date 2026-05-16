package com.fotik.website_for_lesson_schedule.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "specialization")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialization_id")
    private Integer specializationId;

    @Column(name = "specialization_name", nullable = false, length = 200)
    private String specializationName;

    @Column(name = "specialization_code", nullable = false, unique = true, length = 20)
    private String specializationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentGroup> groups;

    public Specialization() {
    }

    public Specialization(String specializationName, String specializationCode, Faculty faculty) {
        this.specializationName = specializationName;
        this.specializationCode = specializationCode;
        this.faculty = faculty;
    }

    // ── Getters & Setters ──

    public Integer getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Integer specializationId) {
        this.specializationId = specializationId;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public void setSpecializationName(String specializationName) {
        this.specializationName = specializationName;
    }

    public String getSpecializationCode() {
        return specializationCode;
    }

    public void setSpecializationCode(String specializationCode) {
        this.specializationCode = specializationCode;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public List<StudentGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<StudentGroup> groups) {
        this.groups = groups;
    }
}