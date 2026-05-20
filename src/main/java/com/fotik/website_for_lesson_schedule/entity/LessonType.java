package com.fotik.website_for_lesson_schedule.entity;

public enum LessonType {
    lecture("lecture"),
    seminar("seminar"),
    lab("lab");

    private final String value;

    LessonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}