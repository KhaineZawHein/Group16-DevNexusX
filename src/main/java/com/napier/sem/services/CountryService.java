package com.napier.sem.services;

import com.napier.sem.models.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CountryService {
    private final Connection connection;

    public CountryService(Connection connection) {
        this.connection = Objects.requireNonNull(
                connection, "Database connection is required."
        );
    }

    // Report 1: All countries in the world by descending population.
    public List<Country> getAllCountriesByPopulation() {
        String sql =
                "SELECT c.Code, c.Name, c.Continent, c.Region, " +
                        "c.Population, capitalCity.Name AS Capital " +
                        "FROM country c " +
                        "LEFT JOIN city capitalCity ON c.Capital = capitalCity.ID " +
                        "ORDER BY c.Population DESC, c.Code ASC";

        List<Country> countries = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {

            while (results.next()) {
                Country country = new Country();

                country.setCode(results.getString("Code"));
                country.setName(results.getString("Name"));
                country.setContinent(results.getString("Continent"));
                country.setRegion(results.getString("Region"));
                country.setPopulation(results.getLong("Population"));

                String capital = results.getString("Capital");
                country.setCapital(capital == null ? "N/A" : capital);

                countries.add(country);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Unable to generate the world countries report.", e
            );
        }

        return countries;
    }
}