package com.napier.sem.models;

public class Country {
    private String code;
    private String name;
    private String continent;
    private String region;
    private long population;
    private String capital;

    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public long getPopulation() { return population; }
    public void setPopulation(long population) { this.population = population; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    // toString method for nice console output
    @Override
    public String toString() {
        return String.format("%-5s | %-50s | %-15s | %-17s | %-15s | %-9s",
                code, name, continent, region, population, capital);
    }
}