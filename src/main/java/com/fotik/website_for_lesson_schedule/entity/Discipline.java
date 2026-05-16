package com.fotik.website_for_lesson_schedule.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "discipline")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discipline_id")
    private Integer disciplineId;

    @Column(name = "discipline_name", nullable = false, length = 200)
    private String disciplineName;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "hours_total", nullable = false)
    private Integer hoursTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    public Discipline() {
    }

    public Discipline(String disciplineName, Integer credits,
                      Integer hoursTotal, Department department) {
        this.disciplineName = disciplineName;
        this.credits = credits;
        this.hoursTotal = hoursTotal;
        this.department = department;
    }

    // ── Getters & Setters ──

    public Integer getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Integer disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getHoursTotal() {
        return hoursTotal;
    }

    public void setHoursTotal(Integer hoursTotal) {
        this.hoursTotal = hoursTotal;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}