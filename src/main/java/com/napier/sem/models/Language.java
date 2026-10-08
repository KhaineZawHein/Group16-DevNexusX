package com.napier.sem.models;

public class Language {
    private String name;
    private long speakers;
    private double worldPercentage;

    // Getters
    public String getName() { return name; }
    public long getSpeakers() { return speakers; }
    public double getWorldPercentage() { return worldPercentage; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setSpeakers(long speakers) { this.speakers = speakers; }
    public void setWorldPercentage(double worldPercentage) { this.worldPercentage = worldPercentage; }

    @Override
    public String toString() {
        return String.format("%-15s | %-15s | %-10s%%", name, speakers, String.format("%.2f", worldPercentage));
    }
}