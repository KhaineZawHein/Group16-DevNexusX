package com.napier.sem;

import com.napier.sem.models.Country;
import com.napier.sem.models.Language;
import com.napier.sem.models.City;
import com.napier.sem.models.Population;
import com.napier.sem.services.CountryService;
import com.napier.sem.services.LanguageService;
import com.napier.sem.services.CityService;
import com.napier.sem.services.PopulationService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Note: Change this back to "localhost" if you changed it for Docker!
        String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
        String url = "jdbc:mysql://" + dbHost + ":3306/world?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = System.getenv().getOrDefault("DB_PASSWORD", "Kzh123!@#");

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connected successfully!\n");

            // 1. Country Report (Top 10)
            CountryService countryService = new CountryService(con);
            List<Country> countries = countryService.getAllCountriesByPopulation();

            System.out.println("Top 10 Countries by Population:");
            System.out.println("Code  | Name                                               | Continent       | Region          | Population      | Capital   ");
            System.out.println("---------------------------------------------------------------------------------------------------------------");

            int count = 0;
            for (Country c : countries) {
                System.out.println(c.toString());
                count++;
                if (count >= 10) break;
            }

            // 2. Capital City Report (Top 10)
            CityService cityService = new CityService(con);
            List<City> capitalCities = cityService.getAllCapitalCitiesByPopulation();

            System.out.println("\nTop 10 Capital Cities by Population:");
            System.out.println("City Name            | Country Name         | Population");
            System.out.println("--------------------------------------------------------");

            count = 0;
            for (City c : capitalCities) {
                System.out.printf("%-20s | %-20s | %-10s%n", c.getName(), c.getCountryName(), c.getPopulation());
                count++;
                if (count >= 10) break;
            }

            // 3. All Cities in the World (Top 10)
            List<City> allCities = cityService.getAllCitiesByPopulation();
            System.out.println("\nTop 10 Cities in the World by Population:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            count = 0;
            for (City c : allCities) {
                System.out.println(c.toString());
                count++;
                if (count >= 10) break;
            }

            // 4. Cities by Country (e.g., GBR - United Kingdom) - Limited to Top 10 for readability
            List<City> gbrCities = cityService.getCitiesByCountry("GBR");
            System.out.println("\nTop 10 Cities in the United Kingdom (GBR):");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            count = 0;
            for (City c : gbrCities) {
                System.out.println(c.toString());
                count++;
                if (count >= 10) break;
            }

            // 5. Top N Cities (e.g., Top 5)
            List<City> top5Cities = cityService.getTopNCities(5);
            System.out.println("\nTop 5 Cities in the World:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            for (City c : top5Cities) {
                System.out.println(c.toString());
            }

            // 6. Cities by Continent (e.g., Asia)
            List<City> asiaCities = cityService.getCitiesByContinent("Asia");
            System.out.println("\nTop 10 Cities in Asia:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            count = 0;
            for (City c : asiaCities) {
                System.out.println(c.toString());
                count++;
                if (count >= 10) break;
            }

            // 7. Cities by Region (e.g., Western Europe)
            List<City> westernEuropeCities = cityService.getCitiesByRegion("Western Europe");
            System.out.println("\nTop 10 Cities in Western Europe:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            count = 0;
            for (City c : westernEuropeCities) {
                System.out.println(c.toString());
                count++;
                if (count >= 10) break;
            }

            // 8. Cities by District (e.g., England)
            List<City> englandCities = cityService.getCitiesByDistrict("England");
            System.out.println("\nTop 10 Cities in England District:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            count = 0;
            for (City c : englandCities) {
                System.out.println(c.toString());
                count++;
                if (count >= 10) break;
            }

            // 9. Language Report (Specific 5 languages, ordered by greatest to smallest)
            LanguageService langService = new LanguageService(con);
            List<Language> allLanguages = langService.getAllLanguagesBySpeakers();

            List<String> requiredLanguages = Arrays.asList("Chinese", "English", "Hindi", "Spanish", "Arabic");

            System.out.println("\nLanguage Report (Top 5 Required - Ordered by Speakers):");
            System.out.println("Name            | Speakers        | World %   ");
            System.out.println("-----------------------------------------------");

            for (Language lang : allLanguages) {
                if (requiredLanguages.contains(lang.getName())) {
                    System.out.println(lang.toString());
                }
            }

            // 10. Population Report
            PopulationService popService = new PopulationService(con);

            Population worldPop = popService.getWorldPopulation();
            System.out.println("\nTotal World Population:");
            System.out.println(worldPop.toString());

            List<Population> continents = popService.getPopulationByContinent();
            System.out.println("\nPopulation by Continent:");
            System.out.println("Name            | Total Population");
            System.out.println("----------------------------------");
            for (Population p : continents) {
                System.out.println(p.toString());
            }
            // 11. Top N Countries by Continent (e.g., Top 3 in Europe)
            List<Country> top3Europe = countryService.getTopNCountriesByContinent(3, "Europe");
            System.out.println("\nTop 3 Countries in Europe by Population:");
            System.out.println("Code  | Name                                               | Continent       | Region          | Population      | Capital   ");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            for (Country c : top3Europe) {
                System.out.println(c.toString());
            }

            // 12. Top N Countries by Region (e.g., Top 2 in Western Europe)
            List<Country> top2WesternEurope = countryService.getTopNCountriesByRegion(2, "Western Europe");
            System.out.println("\nTop 2 Countries in Western Europe by Population:");
            System.out.println("Code  | Name                                               | Continent       | Region          | Population      | Capital   ");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            for (Country c : top2WesternEurope) {
                System.out.println(c.toString());
            }
            // 13. Top 10 Cities in a Continent (e.g., Top 10 in Asia)
            List<City> top10AsiaCities = cityService.getTopNCitiesByContinent(10, "Asia");
            System.out.println("\nTop 10 Cities in Asia by Population:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            for (City c : top10AsiaCities) {
                System.out.println(c.toString());
            }

            // 14. Top 10 Cities in a Region (e.g., Top 10 in Western Europe)
            List<City> top10WesternEuropeCities = cityService.getTopNCitiesByRegion(10, "Western Europe");
            System.out.println("\nTop 10 Cities in Western Europe by Population:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            for (City c : top10WesternEuropeCities) {
                System.out.println(c.toString());
            }

            // 15. Top 10 Cities in a Country (e.g., Top 10 in GBR)
            List<City> top10GBRCities = cityService.getTopNCitiesByCountry(10, "GBR");
            System.out.println("\nTop 10 Cities in United Kingdom (GBR) by Population:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            for (City c : top10GBRCities) {
                System.out.println(c.toString());
            }

            // 16. Top 10 Cities in a District (e.g., Top 10 in England)
            List<City> top10EnglandCities = cityService.getTopNCitiesByDistrict(10, "England");
            System.out.println("\nTop 10 Cities in England District by Population:");
            System.out.println("Name                 | Code | District        | Population");
            System.out.println("-----------------------------------------------------------");
            for (City c : top10EnglandCities) {
                System.out.println(c.toString());
            }
            // 13. All Capital Cities in a Continent (e.g., Europe)
            List<City> europeCapitals = cityService.getAllCapitalCitiesByContinent("Europe");
            System.out.println("\nAll Capital Cities in Europe by Population:");
            System.out.println("City Name            | Country Name         | Population");
            System.out.println("--------------------------------------------------------");
            count = 0;
            for (City c : europeCapitals) {
                System.out.printf("%-20s | %-20s | %-10s%n", c.getName(), c.getCountryName(), c.getPopulation());
                count++;
                if (count >= 10) break; // Limited to 10 for console readability
            }

            // 14. All Capital Cities in a Region (e.g., Western Europe)
            List<City> weCapitals = cityService.getAllCapitalCitiesByRegion("Western Europe");
            System.out.println("\nAll Capital Cities in Western Europe by Population:");
            System.out.println("City Name            | Country Name         | Population");
            System.out.println("--------------------------------------------------------");
            for (City c : weCapitals) {
                System.out.printf("%-20s | %-20s | %-10s%n", c.getName(), c.getCountryName(), c.getPopulation());
            }

            // 15. Top N Capital Cities in the World (e.g., Top 10)
            List<City> top10Capitals = cityService.getTopNCapitalCities(10);
            System.out.println("\nTop 10 Capital Cities in the World by Population:");
            System.out.println("City Name            | Country Name         | Population");
            System.out.println("--------------------------------------------------------");
            for (City c : top10Capitals) {
                System.out.printf("%-20s | %-20s | %-10s%n", c.getName(), c.getCountryName(), c.getPopulation());
            }

            // 16. Top N Capital Cities in a Continent (e.g., Top 10 in Asia)
            List<City> top10AsiaCapitals = cityService.getTopNCapitalCitiesByContinent(10, "Asia");
            System.out.println("\nTop 10 Capital Cities in Asia by Population:");
            System.out.println("City Name            | Country Name         | Population");
            System.out.println("--------------------------------------------------------");
            for (City c : top10AsiaCapitals) {
                System.out.printf("%-20s | %-20s | %-10s%n", c.getName(), c.getCountryName(), c.getPopulation());
            }

            // 17. Top N Capital Cities in a Region (e.g., Top 10 in Western Europe)
            List<City> top10WEPCapitals = cityService.getTopNCapitalCitiesByRegion(10, "Western Europe");
            System.out.println("\nTop 10 Capital Cities in Western Europe by Population:");
            System.out.println("City Name            | Country Name         | Population");
            System.out.println("--------------------------------------------------------");
            for (City c : top10WEPCapitals) {
                System.out.printf("%-20s | %-20s | %-10s%n", c.getName(), c.getCountryName(), c.getPopulation());
            }
            // 18. Population of a specific Region (e.g., Western Europe)
            Population regionPop = popService.getPopulationByRegion("Western Europe");
            if (regionPop != null) {
                System.out.println("\nPopulation of Western Europe:");
                System.out.println(regionPop.toString());
            }

            // 19. Population of a specific Country (e.g., United Kingdom)
            Population countryPop = popService.getPopulationByCountry("United Kingdom");
            if (countryPop != null) {
                System.out.println("\nPopulation of United Kingdom:");
                System.out.println(countryPop.toString());
            }

            // 20. Population of a specific District (e.g., England)
            Population districtPop = popService.getPopulationByDistrict("England");
            if (districtPop != null) {
                System.out.println("\nPopulation of England District:");
                System.out.println(districtPop.toString());
            }

            // 21. Population of a specific City (e.g., London)
            Population cityPop = popService.getPopulationByCity("London");
            if (cityPop != null) {
                System.out.println("\nPopulation of London:");
                System.out.println(cityPop.toString());
            }
            // 22. Population Breakdown by Continent (Total, City, Non-City)
            List<Population> continentBreakdown = popService.getPopulationBreakdownByContinent();
            System.out.println("\nPopulation Breakdown by Continent:");
            System.out.println("Name            | Total Population  | City Population (%)   | Non-City Population (%)");
            System.out.println("------------------------------------------------------------------------------------");
            for (Population p : continentBreakdown) {
                System.out.println(p.toString());
            }

            // 23. Population Breakdown by Region (e.g., Western Europe)
            List<Population> regionBreakdown = popService.getPopulationBreakdownByRegion("Western Europe");
            System.out.println("\nPopulation Breakdown for Western Europe:");
            System.out.println("Name            | Total Population  | City Population (%)   | Non-City Population (%)");
            System.out.println("------------------------------------------------------------------------------------");
            for (Population p : regionBreakdown) {
                System.out.println(p.toString());
            }

            // 24. Population Breakdown by Country (e.g., GBR)
            List<Population> countryBreakdown = popService.getPopulationBreakdownByCountry("GBR");
            System.out.println("\nPopulation Breakdown for United Kingdom (GBR):");
            System.out.println("Name            | Total Population  | City Population (%)   | Non-City Population (%)");
            System.out.println("------------------------------------------------------------------------------------");
            for (Population p : countryBreakdown) {
                System.out.println(p.toString());
            }

        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

}