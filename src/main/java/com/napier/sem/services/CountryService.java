package com.napier.sem.services;

import com.napier.sem.models.Country;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryService {
    private final Connection con;

    public CountryService(Connection con) {
        this.con = con;
    }

    // Requirement 1: All countries in the world organised by largest population to smallest
    public List<Country> getAllCountriesByPopulation() {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country ORDER BY Population DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Country c = new Country();
                c.setCode(rs.getString("Code"));
                c.setName(rs.getString("Name"));
                c.setContinent(rs.getString("Continent"));
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("Population"));
                c.setCapital(rs.getString("Capital"));
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching countries: " + e.getMessage());
        }
        return countries;
    }

    // Requirement 2: All countries in a continent organised by largest population to smallest
    public List<Country> getCountriesByContinent(String continent) {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country WHERE Continent = ? ORDER BY Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, continent);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country c = new Country();
                c.setCode(rs.getString("Code"));
                c.setName(rs.getString("Name"));
                c.setContinent(rs.getString("Continent"));
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("Population"));
                c.setCapital(rs.getString("Capital"));
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching countries by continent: " + e.getMessage());
        }
        return countries;
    }

    // Requirement 3: All countries in a region organised by largest population to smallest
    public List<Country> getCountriesByRegion(String region) {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country WHERE Region = ? ORDER BY Population DESC";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, region);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country c = new Country();
                c.setCode(rs.getString("Code"));
                c.setName(rs.getString("Name"));
                c.setContinent(rs.getString("Continent"));
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("Population"));
                c.setCapital(rs.getString("Capital"));
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching countries by region: " + e.getMessage());
        }
        return countries;
    }

    // Requirement 4: The top N populated countries in the world
    public List<Country> getTopNCountries(int n) {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country ORDER BY Population DESC LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, n);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country c = new Country();
                c.setCode(rs.getString("Code"));
                c.setName(rs.getString("Name"));
                c.setContinent(rs.getString("Continent"));
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("Population"));
                c.setCapital(rs.getString("Capital"));
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching top N countries: " + e.getMessage());
        }
        return countries;
    }
    // Requirement 5: The top N populated countries in a continent
    public List<Country> getTopNCountriesByContinent(int n, String continent) {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country WHERE Continent = ? ORDER BY Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, continent);
            stmt.setInt(2, n);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country c = new Country();
                c.setCode(rs.getString("Code"));
                c.setName(rs.getString("Name"));
                c.setContinent(rs.getString("Continent"));
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("Population"));
                c.setCapital(rs.getString("Capital"));
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching top N countries by continent: " + e.getMessage());
        }
        return countries;
    }

    // Requirement 6: The top N populated countries in a region
    public List<Country> getTopNCountriesByRegion(int n, String region) {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT Code, Name, Continent, Region, Population, Capital " +
                "FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, region);
            stmt.setInt(2, n);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Country c = new Country();
                c.setCode(rs.getString("Code"));
                c.setName(rs.getString("Name"));
                c.setContinent(rs.getString("Continent"));
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("Population"));
                c.setCapital(rs.getString("Capital"));
                countries.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching top N countries by region: " + e.getMessage());
        }
        return countries;
    }
}