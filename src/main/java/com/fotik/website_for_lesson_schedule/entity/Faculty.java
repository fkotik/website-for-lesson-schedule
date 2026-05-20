package com.fotik.website_for_lesson_schedule.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Integer facultyId;

    @Column(name = "faculty_name", nullable = false, unique = true, length = 150)
    private String facultyName;

    @Column(name = "dean_name", nullable = false, length = 150)
    private String deanName;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Department> departments;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Specialization> specializations;

    public Faculty() {
    }

    public Faculty(String facultyName, String deanName) {
        this.facultyName = facultyName;
        this.deanName = deanName;
    }

    // ── Getters & Setters ──

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }
}