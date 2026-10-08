package com.napier.sem;

import com.napier.sem.models.Country;
import com.napier.sem.services.CountryService;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CountryServiceTest {

    static Connection conn;

    @BeforeAll
    static void setUp() throws Exception {
        // Connect to your LOCAL MySQL database
        // CHANGE "password" to the actual password you set when installing MySQL!
        String url = "jdbc:mysql://localhost:3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "Kzh123!@#";

        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to local MySQL successfully!");
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (conn != null) conn.close();
    }

    @Test
    void testGetAllCountriesByPopulation() {
        CountryService service = new CountryService(conn);
        List<Country> countries = service.getAllCountriesByPopulation();

        assertNotNull(countries, "Country list should not be null");
        assertFalse(countries.isEmpty(), "Country list should not be empty");
        assertEquals("China", countries.get(0).getName(), "First country should be China");
        assertTrue(countries.size() > 200, "Should have more than 200 countries");
    }

    @Test
    void testCountryDataIntegrity() {
        CountryService service = new CountryService(conn);
        List<Country> countries = service.getAllCountriesByPopulation();

        Country uk = countries.stream()
                .filter(c -> "GBR".equals(c.getCode()))
                .findFirst()
                .orElse(null);

        assertNotNull(uk, "United Kingdom should exist");
        assertEquals("United Kingdom", uk.getName());
        assertEquals("Europe", uk.getContinent());
    }

    @Test
    void testGetCountriesByContinent() {
        CountryService service = new CountryService(conn);
        List<Country> europeanCountries = service.getCountriesByContinent("Europe");

        assertNotNull(europeanCountries);
        assertFalse(europeanCountries.isEmpty());
        assertEquals("Russian Federation", europeanCountries.get(0).getName());
    }

    @Test
    void testGetTopNCountries() {
        CountryService service = new CountryService(conn);
        List<Country> top3 = service.getTopNCountries(3);

        assertNotNull(top3);
        assertEquals(3, top3.size(), "Should return exactly 3 countries");
        assertEquals("China", top3.get(0).getName());
        assertEquals("India", top3.get(1).getName());
        assertEquals("United States", top3.get(2).getName());
    }
    @Test
    void testGetTopNCountriesByContinent() {
        CountryService service = new CountryService(conn);
        // Get Top 3 countries in Europe
        List<Country> top3Europe = service.getTopNCountriesByContinent(3, "Europe");
        assertNotNull(top3Europe, "List should not be null");
        assertEquals(3, top3Europe.size(), "Should return exactly 3 countries");
        // Russian Federation is usually the most populated in Europe in this DB
        assertEquals("Russian Federation", top3Europe.get(0).getName());
        assertEquals("Europe", top3Europe.get(0).getContinent());
    }

    @Test
    void testGetTopNCountriesByRegion() {
        CountryService service = new CountryService(conn);
        // Get Top 2 countries in Western Europe
        List<Country> top2WesternEurope = service.getTopNCountriesByRegion(2, "Western Europe");
        assertNotNull(top2WesternEurope, "List should not be null");
        assertEquals(2, top2WesternEurope.size(), "Should return exactly 2 countries");
        // Germany is usually the most populated in Western Europe in this DB
        assertEquals("Germany", top2WesternEurope.get(0).getName());
        assertEquals("Western Europe", top2WesternEurope.get(0).getRegion());
    }
}