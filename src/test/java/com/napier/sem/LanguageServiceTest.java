package com.napier.sem;

import com.napier.sem.models.Language;
import com.napier.sem.services.LanguageService;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LanguageServiceTest {

    static Connection conn;

    @BeforeAll
    static void setUp() throws Exception {
        String url = "jdbc:mysql://localhost:3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "Kzh123!@#";

        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Test Database connected successfully!");
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (conn != null) conn.close();
    }

    @Test
    void testGetAllLanguagesBySpeakers() {
        LanguageService service = new LanguageService(conn);
        List<Language> languages = service.getAllLanguagesBySpeakers();

        assertNotNull(languages, "Language list should not be null");
        assertFalse(languages.isEmpty(), "Language list should not be empty");
        assertEquals("Chinese", languages.get(0).getName(), "First language should be Chinese");
        assertTrue(languages.size() > 400, "Should have more than 400 languages");
    }

    @Test
    void testLanguageDataIntegrity() {
        LanguageService service = new LanguageService(conn);
        List<Language> languages = service.getAllLanguagesBySpeakers();

        Language english = languages.stream()
                .filter(l -> "English".equals(l.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(english, "English should exist in the database");
        assertTrue(english.getSpeakers() > 0, "English should have speakers");
        assertTrue(english.getWorldPercentage() > 0, "English should have a world percentage");
    }

    @Test
    void testGetLanguageByName() {
        LanguageService service = new LanguageService(conn);

        Language chinese = service.getLanguageByName("Chinese");
        assertNotNull(chinese, "Chinese language should exist");
        assertEquals("Chinese", chinese.getName());
        assertTrue(chinese.getSpeakers() > 1000000000, "Chinese should have over 1 billion speakers");

        Language english = service.getLanguageByName("English");
        assertNotNull(english, "English language should exist");
    }
}