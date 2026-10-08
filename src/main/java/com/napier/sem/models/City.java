package com.napier.sem.models;

public class City {
    private String name;
    private String countryCode;
    private String district;
    private String countryName;
    private int population;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    @Override
    public String toString() {
        return String.format("%-20s | %-5s | %-15s | %-10s", name, countryCode, district, population);
    }
}