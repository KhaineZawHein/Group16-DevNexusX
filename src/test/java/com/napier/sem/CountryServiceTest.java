package com.napier.sem;

import com.napier.sem.models.Country;
import com.napier.sem.services.CountryService;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class CountryServiceTest {

    // 1. Spin up a temporary MySQL 8.0 database in Docker
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("world")
            .withUsername("test")
            .withPassword("test");

    static Connection conn;

    // 2. Setup runs once before all tests
    @BeforeAll
    static void setUp() throws Exception {
        // Wait for the Docker container to be fully ready
        while (!mysql.isRunning()) {
            Thread.sleep(1000);
        }

        // Connect to the database.
        // allowMultiQueries=true is REQUIRED to run the whole SQL dump file at once.
        String jdbcUrl = mysql.getJdbcUrl() + "?allowMultiQueries=true";
        conn = DriverManager.getConnection(jdbcUrl, mysql.getUsername(), mysql.getPassword());

        // Read the world.sql file from your test resources folder
        // Make sure world.sql is in src/test/resources/world.sql
        String sql = new String(Files.readAllBytes(Paths.get("src/test/resources/world.sql")));

        // Execute the SQL dump to create tables and insert data
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
        System.out.println("Database initialized successfully with world.sql!");
    }

    // 3. Cleanup runs once after all tests
    @AfterAll
    static void tearDown() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    // --- TESTS ---

    @Test
    void testDatabaseTablesExist() throws Exception {
        // Verify that the SQL dump actually created the tables
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES")) {

            assertTrue(rs.next(), "Tables should exist in the database");
            assertEquals("city", rs.getString(1));

            assertTrue(rs.next());
            assertEquals("country", rs.getString(1));

            assertTrue(rs.next());
            assertEquals("countrylanguage", rs.getString(1));
        }
    }

    @Test
    void testGetAllCountriesByPopulation() {
        // Initialize your service with the test database connection
        CountryService service = new CountryService(conn);

        // Call the method we want to test
        List<Country> countries = service.getAllCountriesByPopulation();

        // Assertions to verify the results
        assertNotNull(countries, "Country list should not be null");
        assertFalse(countries.isEmpty(), "Country list should not be empty");

        // In the world database, China is the most populous country
        assertEquals("China", countries.get(0).getName(), "First country should be China");

        // The world database typically has 239 countries/territories
        assertTrue(countries.size() > 200, "Should have more than 200 countries");
    }

    @Test
    void testCountryDataIntegrity() {
        CountryService service = new CountryService(conn);
        List<Country> countries = service.getAllCountriesByPopulation();

        // Find the United Kingdom to test specific data mapping
        Country uk = countries.stream()
                .filter(c -> "GBR".equals(c.getCode()))
                .findFirst()
                .orElse(null);

        assertNotNull(uk, "United Kingdom should exist in the database");
        assertEquals("United Kingdom", uk.getName());
        assertEquals("Europe", uk.getContinent());
        assertTrue(uk.getPopulation() > 50000000, "UK population should be over 50 million");
    }

    @Test
    void testGetCountriesByContinent() {
        CountryService service = new CountryService(conn);

        // Test fetching countries in Europe
        List<Country> europeanCountries = service.getCountriesByContinent("Europe");

        assertNotNull(europeanCountries);
        assertFalse(europeanCountries.isEmpty());

        // Check that the first one is the most populated in Europe (Russia)
        assertEquals("Russian Federation", europeanCountries.get(0).getName());
    }

    @Test
    void testGetTopNCountries() {
        CountryService service = new CountryService(conn);

        // Test fetching top 3 countries
        List<Country> top3 = service.getTopNCountries(3);

        assertNotNull(top3);
        assertEquals(3, top3.size(), "Should return exactly 3 countries");

        // Verify the order
        assertEquals("China", top3.get(0).getName());
        assertEquals("India", top3.get(1).getName());
        assertEquals("United States", top3.get(2).getName());
    }
}