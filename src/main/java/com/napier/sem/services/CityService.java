package com.napier.sem.services;

import com.napier.sem.models.City;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityService {
    private final Connection con;

    public CityService(Connection con) { this.con = con; }

    // 1. All Capital Cities (Existing)
    public List<City> getAllCapitalCitiesByPopulation() {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name AS name, country.Name AS countryName, city.Population AS population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("name"));
                c.setCountryName(rs.getString("countryName"));
                c.setPopulation(rs.getInt("population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // 2. All Cities in the World
    public List<City> getAllCitiesByPopulation() {
        List<City> cities = new ArrayList<>();
        String query = "SELECT Name, CountryCode, District, Population FROM city ORDER BY Population DESC";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // 3. Cities by Country Code
    public List<City> getCitiesByCountry(String countryCode) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT Name, CountryCode, District, Population FROM city WHERE CountryCode = ? ORDER BY Population DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, countryCode);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // 4. Top N Cities in the World
    public List<City> getTopNCities(int n) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT Name, CountryCode, District, Population FROM city ORDER BY Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }
    // 5. Get all cities in a specific Continent
    public List<City> getCitiesByContinent(String continent) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = ? ORDER BY city.Population DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, continent);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // 6. Get all cities in a specific Region
    public List<City> getCitiesByRegion(String region) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ? ORDER BY city.Population DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, region);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // 7. Get all cities in a specific District
    public List<City> getCitiesByDistrict(String district) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT Name, CountryCode, District, Population FROM city WHERE District = ? ORDER BY Population DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, district);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }
    // Requirement: Top N populated cities in a continent
    public List<City> getTopNCitiesByContinent(int n, String continent) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: Top N populated cities in a region
    public List<City> getTopNCitiesByRegion(int n, String region) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name, city.CountryCode, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: Top N populated cities in a country
    public List<City> getTopNCitiesByCountry(int n, String countryCode) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT Name, CountryCode, District, Population FROM city WHERE CountryCode = ? ORDER BY Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, countryCode);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: Top N populated cities in a district
    public List<City> getTopNCitiesByDistrict(int n, String district) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT Name, CountryCode, District, Population FROM city WHERE District = ? ORDER BY Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, district);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("Name"));
                c.setCountryCode(rs.getString("CountryCode"));
                c.setDistrict(rs.getString("District"));
                c.setPopulation(rs.getInt("Population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }
    // Requirement: All capital cities in a continent
    public List<City> getAllCapitalCitiesByContinent(String continent) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name AS name, country.Name AS countryName, city.Population AS population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? ORDER BY city.Population DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, continent);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("name"));
                c.setCountryName(rs.getString("countryName"));
                c.setPopulation(rs.getInt("population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: All capital cities in a region
    public List<City> getAllCapitalCitiesByRegion(String region) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name AS name, country.Name AS countryName, city.Population AS population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? ORDER BY city.Population DESC";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, region);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("name"));
                c.setCountryName(rs.getString("countryName"));
                c.setPopulation(rs.getInt("population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: Top N populated capital cities in the world
    public List<City> getTopNCapitalCities(int n) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name AS name, country.Name AS countryName, city.Population AS population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("name"));
                c.setCountryName(rs.getString("countryName"));
                c.setPopulation(rs.getInt("population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: Top N populated capital cities in a continent
    public List<City> getTopNCapitalCitiesByContinent(int n, String continent) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name AS name, country.Name AS countryName, city.Population AS population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("name"));
                c.setCountryName(rs.getString("countryName"));
                c.setPopulation(rs.getInt("population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }

    // Requirement: Top N populated capital cities in a region
    public List<City> getTopNCapitalCitiesByRegion(int n, String region) {
        List<City> cities = new ArrayList<>();
        String query = "SELECT city.Name AS name, country.Name AS countryName, city.Population AS population " +
                "FROM city JOIN country ON city.ID = country.Capital " +
                "WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                City c = new City();
                c.setName(rs.getString("name"));
                c.setCountryName(rs.getString("countryName"));
                c.setPopulation(rs.getInt("population"));
                cities.add(c);
            }
        } catch (SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return cities;
    }
}