package com.napier.sem;

import com.napier.sem.models.Population;
import com.napier.sem.services.PopulationService;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PopulationServiceTest {
    static Connection conn;

    @BeforeAll
    static void setUp() throws Exception {
        String url = "jdbc:mysql://localhost:3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = System.getenv().getOrDefault("DB_PASSWORD", "Kzh123!@#");
        conn = DriverManager.getConnection(url, user, password);
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (conn != null) conn.close();
    }

    @Test
    void testGetWorldPopulation() {
        PopulationService service = new PopulationService(conn);
        Population world = service.getWorldPopulation();
        assertNotNull(world);
        assertEquals("World", world.getName());
        // World population in this DB is usually around 6 billion
        assertTrue(world.getTotalPopulation() > 5000000000L);
    }

    @Test
    void testGetPopulationByContinent() {
        PopulationService service = new PopulationService(conn);
        List<Population> continents = service.getPopulationByContinent();
        assertNotNull(continents);
        assertFalse(continents.isEmpty());
        // Asia is usually the most populated continent
        assertEquals("Asia", continents.get(0).getName());
    }
    @Test
    void testGetPopulationByRegion() {
        PopulationService service = new PopulationService(conn);
        Population regionPop = service.getPopulationByRegion("Western Europe");
        assertNotNull(regionPop, "Population should not be null");
        assertEquals("Western Europe", regionPop.getName());
        assertTrue(regionPop.getTotalPopulation() > 0);
    }

    @Test
    void testGetPopulationByCountry() {
        PopulationService service = new PopulationService(conn);
        Population countryPop = service.getPopulationByCountry("United Kingdom");
        assertNotNull(countryPop, "Population should not be null");
        assertEquals("United Kingdom", countryPop.getName());
        assertTrue(countryPop.getTotalPopulation() > 50000000);
    }

    @Test
    void testGetPopulationByDistrict() {
        PopulationService service = new PopulationService(conn);
        Population districtPop = service.getPopulationByDistrict("England");
        assertNotNull(districtPop, "Population should not be null");
        assertEquals("England", districtPop.getName());
        assertTrue(districtPop.getTotalPopulation() > 0);
    }

    @Test
    void testGetPopulationByCity() {
        PopulationService service = new PopulationService(conn);
        Population cityPop = service.getPopulationByCity("London");
        assertNotNull(cityPop, "Population should not be null");
        assertEquals("London", cityPop.getName());
        assertEquals(7285000, cityPop.getTotalPopulation());
    }
    @Test
    void testGetPopulationBreakdownByContinent() {
        PopulationService service = new PopulationService(conn);
        List<Population> breakdown = service.getPopulationBreakdownByContinent();
        assertNotNull(breakdown, "List should not be null");
        assertFalse(breakdown.isEmpty(), "List should not be empty");
        // Check Asia specifically
        Population asia = breakdown.stream().filter(p -> "Asia".equals(p.getName())).findFirst().orElse(null);
        assertNotNull(asia, "Asia should exist in breakdown");
        assertTrue(asia.getTotalPopulation() > 0);
        assertTrue(asia.getCityPopulation() > 0);
        assertTrue(asia.getNonCityPopulation() > 0);
    }

    @Test
    void testGetPopulationBreakdownByRegion() {
        PopulationService service = new PopulationService(conn);
        List<Population> breakdown = service.getPopulationBreakdownByRegion("Western Europe");
        assertNotNull(breakdown, "List should not be null");
        assertEquals(1, breakdown.size(), "Should return exactly 1 region");
        Population we = breakdown.get(0);
        assertEquals("Western Europe", we.getName());
        assertTrue(we.getTotalPopulation() > 0);
        assertTrue(we.getCityPopulation() > 0);
    }

    @Test
    void testGetPopulationBreakdownByCountry() {
        PopulationService service = new PopulationService(conn);
        List<Population> breakdown = service.getPopulationBreakdownByCountry("GBR");
        assertNotNull(breakdown, "List should not be null");
        assertEquals(1, breakdown.size(), "Should return exactly 1 country");
        Population gbr = breakdown.get(0);
        assertEquals("United Kingdom", gbr.getName());
        assertTrue(gbr.getTotalPopulation() > 0);
        assertTrue(gbr.getCityPopulation() > 0);
    }
}