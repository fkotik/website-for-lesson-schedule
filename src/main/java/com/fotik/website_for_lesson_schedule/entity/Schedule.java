package com.fotik.website_for_lesson_schedule.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "schedule",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"teacher_id", "timeslot_id", "date"}),
                @UniqueConstraint(columnNames = {"group_id", "timeslot_id", "date"}),
                @UniqueConstraint(columnNames = {"classroom_id", "timeslot_id", "date"})
        })
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id", nullable = false)
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id", nullable = false)
    private TimeSlot timeslot;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type", nullable = false)
    private LessonType lessonType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Schedule() {
    }

    public Schedule(Discipline discipline, Teacher teacher, StudentGroup group,
                    Classroom classroom, TimeSlot timeslot, LessonType lessonType,
                    LocalDate date) {
        this.discipline = discipline;
        this.teacher = teacher;
        this.group = group;
        this.classroom = classroom;
        this.timeslot = timeslot;
        this.lessonType = lessonType;
        this.date = date;
    }

    // ── Getters & Setters ──

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}