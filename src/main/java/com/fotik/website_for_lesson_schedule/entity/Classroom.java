package com.fotik.website_for_lesson_schedule.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "classroom",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_number", "building"}))
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private Integer classroomId;

    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;

    @Column(name = "building", nullable = false, length = 100)
    private String building;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "classroom_type", length = 100)
    private String classroomType;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    public Classroom() {
    }

    public Classroom(String roomNumber, String building,
                     Integer capacity, String classroomType) {
        this.roomNumber = roomNumber;
        this.building = building;
        this.capacity = capacity;
        this.classroomType = classroomType;
    }

    // ── Getters & Setters ──

    public Integer getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getClassroomType() {
        return classroomType;
    }

    public void setClassroomType(String classroomType) {
        this.classroomType = classroomType;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}