package com.napier.sem.models;

public class Population {
    private String name;
    private long totalPopulation;
    private long cityPopulation;
    private long nonCityPopulation;

    // Existing Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getTotalPopulation() { return totalPopulation; }
    public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation; }

    // New Getters and Setters for Breakdown
    public long getCityPopulation() { return cityPopulation; }
    public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation; }
    public long getNonCityPopulation() { return nonCityPopulation; }
    public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation; }

    // Updated toString to handle both simple and breakdown reports
    @Override
    public String toString() {
        // If it's a breakdown report, show the percentages
        if (cityPopulation > 0 || nonCityPopulation > 0) {
            double cityPercent = totalPopulation > 0 ? (cityPopulation * 100.0 / totalPopulation) : 0;
            double nonCityPercent = totalPopulation > 0 ? (nonCityPopulation * 100.0 / totalPopulation) : 0;

            return String.format("%-15s | %-15s | %-15s (%.2f%%) | %-15s (%.2f%%)",
                    name, totalPopulation, cityPopulation, cityPercent, nonCityPopulation, nonCityPercent);
        }
        // Otherwise, just show the simple total
        return String.format("%-15s | %-15s", name, totalPopulation);
    }
}