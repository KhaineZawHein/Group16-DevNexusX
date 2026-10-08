package com.napier.sem;

import com.napier.sem.models.City;
import com.napier.sem.services.CityService;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CityServiceTest {
    static Connection conn;

    @BeforeAll
    static void setUp() throws Exception {
        String url = "jdbc:mysql://localhost:3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "Kzh123!@#";
        conn = DriverManager.getConnection(url, user, password);
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (conn != null) conn.close();
    }

    @Test
    void testGetAllCapitalCities() {
        CityService service = new CityService(conn);
        List<City> cities = service.getAllCapitalCitiesByPopulation();
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertEquals("Seoul", cities.get(0).getName());
    }

    @Test
    void testGetAllCities() {
        CityService service = new CityService(conn);
        List<City> cities = service.getAllCitiesByPopulation();
        assertNotNull(cities);
        assertTrue(cities.size() > 4000);
    }

    @Test
    void testGetCitiesByCountry() {
        CityService service = new CityService(conn);
        List<City> cities = service.getCitiesByCountry("GBR");
        assertNotNull(cities);
        assertEquals("London", cities.get(0).getName());
    }

    @Test
    void testGetTopNCities() {
        CityService service = new CityService(conn);
        List<City> cities = service.getTopNCities(5);
        assertNotNull(cities);
        assertEquals(5, cities.size());
    }

    @Test
    void testGetCitiesByContinent() {
        CityService service = new CityService(conn);
        List<City> cities = service.getCitiesByContinent("Asia");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        // Mumbai or Seoul should be top in Asia
        assertTrue(cities.get(0).getPopulation() > 9000000);
    }

    @Test
    void testGetCitiesByRegion() {
        CityService service = new CityService(conn);
        List<City> cities = service.getCitiesByRegion("Western Europe");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertEquals("Berlin", cities.get(0).getName()); // London is top in Western Europe
    }

    @Test
    void testGetCitiesByDistrict() {
        CityService service = new CityService(conn);
        List<City> cities = service.getCitiesByDistrict("England");
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
        assertEquals("London", cities.get(0).getName());
    }
    @Test
    void testGetTopNCitiesByContinent() {
        CityService service = new CityService(conn);
        // Get Top 3 cities in Asia
        List<City> top3Asia = service.getTopNCitiesByContinent(3, "Asia");
        assertNotNull(top3Asia, "List should not be null");
        assertEquals(3, top3Asia.size(), "Should return exactly 3 cities");
        assertEquals("Mumbai (Bombay)", top3Asia.get(0).getName());
    }

    @Test
    void testGetTopNCitiesByRegion() {
        CityService service = new CityService(conn);
        // Get Top 2 cities in Western Europe
        List<City> top2WesternEurope = service.getTopNCitiesByRegion(2, "Western Europe");
        assertNotNull(top2WesternEurope, "List should not be null");
        assertEquals(2, top2WesternEurope.size(), "Should return exactly 2 cities");
        assertEquals("Berlin", top2WesternEurope.get(0).getName());
    }

    @Test
    void testGetTopNCitiesByCountry() {
        CityService service = new CityService(conn);
        // Get Top 3 cities in United Kingdom (GBR)
        List<City> top3GBR = service.getTopNCitiesByCountry(3, "GBR");
        assertNotNull(top3GBR, "List should not be null");
        assertEquals(3, top3GBR.size(), "Should return exactly 3 cities");
        assertEquals("London", top3GBR.get(0).getName());
    }

    @Test
    void testGetTopNCitiesByDistrict() {
        CityService service = new CityService(conn);
        // Get Top 2 cities in England district
        List<City> top2England = service.getTopNCitiesByDistrict(2, "England");
        assertNotNull(top2England, "List should not be null");
        assertEquals(2, top2England.size(), "Should return exactly 2 cities");
        assertEquals("London", top2England.get(0).getName());
    }
    @Test
    void testGetAllCapitalCitiesByContinent() {
        CityService service = new CityService(conn);
        List<City> capitals = service.getAllCapitalCitiesByContinent("Europe");
        assertNotNull(capitals, "List should not be null");
        assertFalse(capitals.isEmpty(), "List should not be empty");
        // Moscow is typically the most populated capital in Europe in this DB
        assertEquals("Moscow", capitals.get(0).getName());
    }

    @Test
    void testGetAllCapitalCitiesByRegion() {
        CityService service = new CityService(conn);
        List<City> capitals = service.getAllCapitalCitiesByRegion("Western Europe");
        assertNotNull(capitals, "List should not be null");
        assertFalse(capitals.isEmpty(), "List should not be empty");
        // Paris or Berlin will be at the top
        assertTrue(capitals.get(0).getPopulation() > 2000000);
    }

    @Test
    void testGetTopNCapitalCities() {
        CityService service = new CityService(conn);
        List<City> top3Capitals = service.getTopNCapitalCities(3);
        assertNotNull(top3Capitals, "List should not be null");
        assertEquals(3, top3Capitals.size(), "Should return exactly 3 capitals");
        assertEquals("Seoul", top3Capitals.get(0).getName());
    }

    @Test
    void testGetTopNCapitalCitiesByContinent() {
        CityService service = new CityService(conn);
        List<City> top2CapitalsAsia = service.getTopNCapitalCitiesByContinent(2, "Asia");
        assertNotNull(top2CapitalsAsia, "List should not be null");
        assertEquals(2, top2CapitalsAsia.size(), "Should return exactly 2 capitals");
        assertEquals("Seoul", top2CapitalsAsia.get(0).getName());
    }

    @Test
    void testGetTopNCapitalCitiesByRegion() {
        CityService service = new CityService(conn);
        List<City> top2CapitalsWE = service.getTopNCapitalCitiesByRegion(2, "Western Europe");
        assertNotNull(top2CapitalsWE, "List should not be null");
        assertEquals(2, top2CapitalsWE.size(), "Should return exactly 2 capitals");
        assertTrue(top2CapitalsWE.get(0).getPopulation() > 2000000);
    }
}