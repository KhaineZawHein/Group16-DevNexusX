package com.napier.sem.services;

import com.napier.sem.models.Population;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationService {
    private final Connection con;

    public PopulationService(Connection con) { this.con = con; }

    public Population getWorldPopulation() {
        String query = "SELECT SUM(Population) AS total FROM country";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Population p = new Population();
                p.setName("World");
                p.setTotalPopulation(rs.getLong("total"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching world population: " + e.getMessage());
        }
        return null;
    }

    public List<Population> getPopulationByContinent() {
        List<Population> populations = new ArrayList<>();
        String query = "SELECT Continent AS name, SUM(Population) AS total FROM country GROUP BY Continent ORDER BY total DESC";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("name"));
                p.setTotalPopulation(rs.getLong("total"));
                populations.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching continent population: " + e.getMessage());
        }
        return populations;
    }
    // Requirement: Population of a specific Region
    public Population getPopulationByRegion(String region) {
        String query = "SELECT Region AS name, SUM(Population) AS total FROM country WHERE Region = ? GROUP BY Region";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, region);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("name"));
                p.setTotalPopulation(rs.getLong("total"));
                return p;
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return null;
    }

    // Requirement: Population of a specific Country
    public Population getPopulationByCountry(String countryName) {
        String query = "SELECT Name, Population FROM country WHERE Name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, countryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("Name"));
                p.setTotalPopulation(rs.getLong("Population"));
                return p;
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return null;
    }

    // Requirement: Population of a specific District
    public Population getPopulationByDistrict(String district) {
        String query = "SELECT District AS name, SUM(Population) AS total FROM city WHERE District = ? GROUP BY District";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, district);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("name"));
                p.setTotalPopulation(rs.getLong("total"));
                return p;
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return null;
    }

    // Requirement: Population of a specific City
    public Population getPopulationByCity(String cityName) {
        String query = "SELECT Name, Population FROM city WHERE Name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cityName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("Name"));
                p.setTotalPopulation(rs.getLong("Population"));
                return p;
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return null;
    }
    // Requirement: Population Breakdown by Continent (FIXED)
    public List<Population> getPopulationBreakdownByContinent() {
        List<Population> populations = new ArrayList<>();
        String query = "SELECT co.Continent AS name, " +
                "SUM(co.Population) AS totalPop, " +
                "COALESCE(SUM(city_agg.cityPop), 0) AS cityPop, " +
                "(SUM(co.Population) - COALESCE(SUM(city_agg.cityPop), 0)) AS nonCityPop " +
                "FROM country co LEFT JOIN (SELECT CountryCode, SUM(Population) AS cityPop FROM city GROUP BY CountryCode) AS city_agg ON co.Code = city_agg.CountryCode " +
                "GROUP BY co.Continent ORDER BY totalPop DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("name"));
                p.setTotalPopulation(rs.getLong("totalPop"));
                p.setCityPopulation(rs.getLong("cityPop"));
                p.setNonCityPopulation(rs.getLong("nonCityPop"));
                populations.add(p);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return populations;
    }

    // Requirement: Population Breakdown by Region (FIXED)
    public List<Population> getPopulationBreakdownByRegion(String region) {
        List<Population> populations = new ArrayList<>();
        String query = "SELECT co.Region AS name, " +
                "SUM(co.Population) AS totalPop, " +
                "COALESCE(SUM(city_agg.cityPop), 0) AS cityPop, " +
                "(SUM(co.Population) - COALESCE(SUM(city_agg.cityPop), 0)) AS nonCityPop " +
                "FROM country co LEFT JOIN (SELECT CountryCode, SUM(Population) AS cityPop FROM city GROUP BY CountryCode) AS city_agg ON co.Code = city_agg.CountryCode " +
                "WHERE co.Region = ? GROUP BY co.Region";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, region);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("name"));
                p.setTotalPopulation(rs.getLong("totalPop"));
                p.setCityPopulation(rs.getLong("cityPop"));
                p.setNonCityPopulation(rs.getLong("nonCityPop"));
                populations.add(p);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return populations;
    }

    // Requirement: Population Breakdown by Country (FIXED)
    public List<Population> getPopulationBreakdownByCountry(String countryCode) {
        List<Population> populations = new ArrayList<>();
        String query = "SELECT co.Name AS name, " +
                "SUM(co.Population) AS totalPop, " +
                "COALESCE(SUM(city_agg.cityPop), 0) AS cityPop, " +
                "(SUM(co.Population) - COALESCE(SUM(city_agg.cityPop), 0)) AS nonCityPop " +
                "FROM country co LEFT JOIN (SELECT CountryCode, SUM(Population) AS cityPop FROM city GROUP BY CountryCode) AS city_agg ON co.Code = city_agg.CountryCode " +
                "WHERE co.Code = ? GROUP BY co.Name, co.Code";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, countryCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Population p = new Population();
                p.setName(rs.getString("name"));
                p.setTotalPopulation(rs.getLong("totalPop"));
                p.setCityPopulation(rs.getLong("cityPop"));
                p.setNonCityPopulation(rs.getLong("nonCityPop"));
                populations.add(p);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return populations;
    }

}