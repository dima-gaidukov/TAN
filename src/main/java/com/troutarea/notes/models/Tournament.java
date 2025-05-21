package com.troutarea.notes.models;

public class Tournament {
    private int id; // Добавляем поле для идентификатора
    private String name;
    private String date;
    private String imagePath;
    private Venue venue;

    public Tournament(int id, String name, String date, String imagePath, Venue venue) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.imagePath = imagePath;
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Venue getVenue() {
        return venue;
    }

    @Override
    public String toString() {
        return String.format("Турнир: %s, Дата: %s, Водоем: %s", name, date, venue.getName());
    }
}