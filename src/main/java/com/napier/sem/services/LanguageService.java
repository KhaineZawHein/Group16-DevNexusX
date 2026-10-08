package com.napier.sem.services;

import com.napier.sem.models.Language;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageService {
    private final Connection con;

    public LanguageService(Connection con) {
        this.con = con;
    }

    public List<Language> getAllLanguagesBySpeakers() {
        List<Language> languages = new ArrayList<>();
        String query = "SELECT cl.Language AS name, " +
                "CAST(SUM(c.Population * (cl.Percentage / 100)) AS UNSIGNED) AS speakers, " +
                "ROUND((SUM(c.Population * (cl.Percentage / 100)) / (SELECT SUM(Population) FROM country)) * 100, 2) AS worldPercentage " +
                "FROM country c JOIN countrylanguage cl ON c.Code = cl.CountryCode " +
                "GROUP BY cl.Language ORDER BY speakers DESC";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Language l = new Language();
                l.setName(rs.getString("name"));
                l.setSpeakers(rs.getLong("speakers"));
                l.setWorldPercentage(rs.getDouble("worldPercentage"));
                languages.add(l);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching languages: " + e.getMessage());
        }
        return languages;
    }

    public Language getLanguageByName(String languageName) {
        String query = "SELECT cl.Language AS name, " +
                "CAST(SUM(c.Population * (cl.Percentage / 100)) AS UNSIGNED) AS speakers, " +
                "ROUND((SUM(c.Population * (cl.Percentage / 100)) / (SELECT SUM(Population) FROM country)) * 100, 2) AS worldPercentage " +
                "FROM country c JOIN countrylanguage cl ON c.Code = cl.CountryCode " +
                "WHERE cl.Language = ? " +
                "GROUP BY cl.Language";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, languageName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Language l = new Language();
                l.setName(rs.getString("name"));
                l.setSpeakers(rs.getLong("speakers"));
                l.setWorldPercentage(rs.getDouble("worldPercentage"));
                return l;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching language: " + e.getMessage());
        }
        return null;
    }
}