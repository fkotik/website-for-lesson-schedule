package com.fotik.website_for_lesson_schedule.entity;

public enum LessonType {
    LECTURE("lecture"),
    SEMINAR("seminar"),
    LAB("lab");

    private final String value;

    LessonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}