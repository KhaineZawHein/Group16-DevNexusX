package com.napier.sem;

import com.napier.sem.models.Country;
import com.napier.sem.services.CountryService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isBlank()) {
            System.err.println("Please set the DB_PASSWORD environment variable.");
            System.exit(1);
            return;
        }

        String url = "jdbc:mysql://" + host +
                ":3306/world?useSSL=false&allowPublicKeyRetrieval=true";

        try (Connection connection =
                     DriverManager.getConnection(url, "root", password)) {

            CountryService service = new CountryService(connection);
            List<Country> countries = service.getAllCountriesByPopulation();

            System.out.println(
                    "All Countries in the World — Largest to Smallest Population"
            );

            System.out.printf(
                    "%-5s | %-50s | %-15s | %-25s | %15s | %-35s%n",
                    "Code", "Name", "Continent", "Region", "Population", "Capital"
            );

            System.out.println("-".repeat(160));

            for (Country country : countries) {
                System.out.println(country);
            }

            System.out.println("\nTotal countries: " + countries.size());

        } catch (SQLException | IllegalStateException e) {
            System.err.println("Country report failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}