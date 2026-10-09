# Use Case Specifications

## Use Case 1: Generate Country Reports
* **Actor:** Organisation User (Data Analyst)
* **Description:** The user requests a report of countries organized by population.
* **Preconditions:** The user has access to the Population Reporting System and the database is online.
* **Main Flow:** 
  1. The user selects the "Generate Country Reports" option.
  2. The user specifies the scope (World, Continent, or Region) and optionally a number 'N'.
  3. The system queries the World SQL Database.
  4. The system displays the list of countries ordered by population.
* **Postconditions:** The requested report is displayed to the user.

## Use Case 2: Generate City Reports
* **Actor:** Organisation User (Data Analyst)
* **Description:** The user requests a report of cities organized by population.
* **Preconditions:** The user has access to the Population Reporting System and the database is online.
* **Main Flow:** 
  1. The user selects the "Generate City Reports" option.
  2. The user specifies the scope (World, Continent, Region, Country, or District) and optionally a number 'N'.
  3. The system queries the World SQL Database.
  4. The system displays the list of cities ordered by population.
* **Postconditions:** The requested report is displayed to the user.

## Use Case 3: Generate Capital City Reports
* **Actor:** Organisation User (Data Analyst)
* **Description:** The user requests a report of capital cities organized by population.
* **Preconditions:** The user has access to the Population Reporting System and the database is online.
* **Main Flow:** 
  1. The user selects the "Generate Capital City Reports" option.
  2. The user specifies the scope (World, Continent, or Region) and optionally a number 'N'.
  3. The system queries the World SQL Database.
  4. The system displays the list of capital cities ordered by population.
* **Postconditions:** The requested report is displayed to the user.

## Use Case 4: Generate Population Breakdowns
* **Actor:** Organisation User (Data Analyst)
* **Description:** The user requests a breakdown of urban vs rural population.
* **Preconditions:** The user has access to the Population Reporting System and the database is online.
* **Main Flow:** 
  1. The user selects the "Generate Population Breakdowns" option.
  2. The user specifies the scope (Continent, Region, or Country).
  3. The system queries the World SQL Database.
  4. The system displays the total population, city population, and non-city population.
* **Postconditions:** The requested report is displayed to the user.

## Use Case 5: View Specific Populations
* **Actor:** Organisation User (Data Analyst)
* **Description:** The user requests the total population of a specific area.
* **Preconditions:** The user has access to the Population Reporting System and the database is online.
* **Main Flow:** 
  1. The user selects the "View Specific Populations" option.
  2. The user specifies the area (World, Continent, Region, Country, District, or City).
  3. The system queries the World SQL Database.
  4. The system displays the total population of the specified area.
* **Postconditions:** The requested report is displayed to the user.

## Use Case 6: Generate Language Speaker Reports
* **Actor:** Organisation User (Data Analyst)
* **Description:** The user requests a report on the number of people who speak specific languages.
* **Preconditions:** The user has access to the Population Reporting System and the database is online.
* **Main Flow:** 
  1. The user selects the "Generate Language Speaker Reports" option.
  2. The user specifies the language (Chinese, English, Hindi, Spanish, or Arabic).
  3. The system queries the World SQL Database.
  4. The system displays the number of speakers and their percentage of the world population.
* **Postconditions:** The requested report is displayed to the user.
