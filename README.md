# Selenium-RestAssured-Automation-Framework
![Automation Framework](https://img.shields.io/badge/Automation-Framework-brightgreen)

## Table of contents
* [About The Project](#about-the-project)
* [Built With](#built-with)
* [Prerequisites](#prerequisites)
* [Installation and Run](#installation-and-run)
* [Design Patterns & Concepts](#design-patterns-and-concepts)
* [Project Structure](#project-structure)
* [Test Scenarios](#test-scenarios)

## 📌About The Project

This project is a comprehensive test automation framework combining **Selenium WebDriver** for UI testing and **RestAssured** for API testing. The framework is designed to automate e-commerce workflows on [Amazon.eg](https://www.amazon.eg/) and API testing on [Reqres API](https://reqres.in).

### 🔹 **Key Features:**  
✅ **Selenium Web Automation:** Automates the complete Amazon.eg user journey, from login through product filtering to checkout validation.  
✅ **API Testing with RestAssured:** Implements comprehensive user management API tests (Create, Retrieve, Update).  
✅ **TestNG for Execution & Validation:** Ensures structured and reliable test execution.  
✅ **Page Object Model (POM):** Implements a modular and maintainable test structure.  
✅ **Singleton Design Pattern:** Ensures efficient WebDriver management.  
✅ **Robust Logging:** Implements SLF4J with Logback for comprehensive logging.  
✅ **Maven for Dependency Management:** Simplifies project setup and execution.  

This framework follows industry best practices to ensure **scalability, reusability, and reliability** in test automation. 🚀 

## 🛠Built With

This section lists the major frameworks/libraries used to build this project:

* [![Java][Java]][Java-url]
* [![Selenium][Selenium]][Selenium-url]
* [![RestAssured][RestAssured]][RestAssured-url]
* [![TestNG][TestNG]][TestNG-url]
* [![SLF4J][SLF4J]][SLF4J-url]
* [![Logback][Logback]][Logback-url]
* [![JSON][JSON]][JSON-url]

## 📌Prerequisites

Before setting up the project, ensure you have the following installed:  

✅ **Operating System:** Windows, macOS, or Linux  
✅ **Java Development Kit (JDK 21)** – [Download Here](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)  
✅ **Maven (Latest Version)** – [Download Here](https://maven.apache.org/download.cgi)  
✅ **Chrome Browser** – [Download Here](https://www.google.com/chrome/)

## 🚀Installation and Run

Follow these steps to set up and run the project:

1️⃣ Clone the repo
   
```sh
git clone https://github.com/QUALITYSENSEI/Foodics-Task.git
```

2️⃣ Install Dependencies
```sh
mvn clean install
```

3️⃣ Configure Environment Variables
Ensure the following are set in your system:

* JAVA_HOME → Path to JDK 21
* MAVEN_HOME → Path to Maven installation
* PATH → Add Maven and Java bin directories
  
4️⃣ Run UI Tests (Amazon E-commerce Flow)
```sh
mvn test -Dtest=AmazonTest
```

5️⃣ Run API Tests (RestAssured)
```sh
mvn test -Dtest=ApiTest
```

OR run all tests:
```sh
mvn test
```

## 🎯Design Patterns and Concepts
* [Page-Object-Model Pattern **POM**](#pom)
* [Singleton Design Pattern](#singleton-design-pattern)
* [Method Chaining Concept](#method-chaining-concept)
* [JSON Configuration](#json-configuration)
* [Logging Strategy](#logging-strategy)

## POM 🏗️
The framework implements the Page Object Model (POM) design pattern which creates an Object Repository for web UI elements:

* **Separation of Concerns:** POM separates the test code from the page interaction logic.
* **Reduced Code Duplication:** Each page element is defined once.
* **Improved Maintenance:** Changes to the UI require updates in only one place.
* **Enhanced Readability:** Test scripts focus on business flows rather than element interactions.

In our implementation:
- Each page in the application is represented by a separate class (e.g., `HomePage`, `CheckOutPage`)
- Base functionality is abstracted in the `BasePage` class
- Page classes expose methods that represent actions possible on that page

## Singleton Design Pattern 🔄
The Singleton pattern is implemented for WebDriver management through the `WebDriverSingleton` class:

```java
public static WebDriver getDriver() {
    if (driver == null) {
        logger.info("Initializing WebDriver instance.");
        ChromeOptions options = new ChromeOptions();
        // Configure options from properties
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    return driver;
}
```

**Benefits:**
- Ensures only one WebDriver instance exists throughout test execution
- Prevents memory leaks and resource duplication
- Provides centralized WebDriver configuration

## Method Chaining Concept 🔗
The framework implements method chaining to create fluent interfaces for page interactions:

```java
homePage
    .navigateToHomePage()
    .navigateToLoginPage()
    .enterPhoneNumber(phoneNumber)
    .clickOnContinueButton()
    // Additional method calls
```

This approach:
- Improves code readability
- Reduces unnecessary variable declarations
- Creates a more natural flow that mirrors user actions
- Makes test scripts more maintainable

## Logging Strategy 📊
The framework implements a comprehensive logging strategy using SLF4J with Logback:

- **Centralized Configuration:** Logback provides flexible logging configuration.
- **Different Log Levels:** DEBUG, INFO, WARN, ERROR levels are used appropriately.
- **Contextual Information:** Log messages include method names, actions, and element identifiers.

Example logging implementation:
```java
private static final Logger logger = LoggerFactory.getLogger(BasePage.class);

public void clickElement(By locator) {
    logger.info("Clicking element by locator: {}", locator);
    findElement(locator).click();
}
```

## Project Structure
The project follows a clear separation of concerns with distinct packages. Here's an overview of the actual project structure:

```
├── logs/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org.example/
│   │           ├── base/
│   │           │   └── BasePage.java
│   │           ├── factory/
│   │           │   └── WebDriverSingleton.java
│   │           ├── pages/
│   │           │   ├── CartPage.java
│   │           │   ├── CheckOutPage.java
│   │           │   ├── HomePage.java
│   │           │   ├── SignInPage.java
│   │           │   └── VideoGamesPage.java
│   │           ├── utils/
│   │           │   ├── JsonReaderUtil.java
│   │           │   ├── PropertiesReaderUtil.java
│   │           │   └── UserPayloadBuilder.java
│   │           └── resources/
│   │               └── logback.xml
│   └── test/
│       ├── java/
│       │   └── org.example/
│       │       ├── base/
│       │       │   ├── api/
│       │       │   │   └── APIBaseTest.java
│       │       │   └── ui/
│       │       │       └── UIBaseTest.java
│       │       └── tests/
│       │           ├── api/
│       │           │   └── ApiTest.java
│       │           └── ui/
│       │               └── AmazonTest.java
│       └── resources/
│           ├── application.properties
│           └── testData.json
└── target/
```

### Key Components:

- **Main Source (`src/main/java`)**:
  - **Base Package**: Contains `BasePage.java` with common WebDriver interactions
  - **Factory Package**: Implements `WebDriverSingleton.java` for browser session management
  - **Pages Package**: Page Object Model implementation with classes for each page
  - **Utils Package**: Utility classes for configuration and data management

- **Test Source (`src/test/java`)**:
  - **Base Package**:
    - `api/APIBaseTest.java`: Sets up API test configurations
    - `ui/UIBaseTest.java`: Sets up UI test configurations
  - **Tests Package**:
    - `api/ApiTest.java`: API test scenarios for user management
    - `ui/AmazonTest.java`: UI test scenarios for Amazon shopping flow

- **Resources**:
  - `logback.xml`: Logging configuration
  - `application.properties`: Application configuration properties
  - `testData.json`: Test data in JSON format

## Test Scenarios

🛒 **UI Test Scenario (Amazon E-commerce Flow)**
1. Navigate to Amazon Egypt
2. Login using phone number and password
3. Navigate to All Video Games section via the menu
4. Apply filters: Free Shipping and New Condition
5. Sort items by Price: High to Low
6. Add the first available product below 15,000 EGP to cart
7. Validate cart contents and total price
8. Proceed to checkout and enter shipping details
9. Validate the order total calculation including shipping fees

🔗 **API Test Scenarios (User Management API)**
1. **Create a new user**
   - Send POST request with user details
   - Verify 201 Created status
   - Extract and store user ID for subsequent tests

2. **Retrieve an existing user**
   - Send GET request with the user ID
   - Verify 200 OK status
   - Validate response contains correct user details

3. **Update an existing user**
   - Send PUT request with updated details
   - Verify 200 OK status
   - Confirm response contains updated information

[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://docs.oracle.com/en/java/
[Selenium]: https://img.shields.io/badge/selenium-webdriver-43B02A?style=for-the-badge&logo=selenium&logoColor=white
[Selenium-url]: https://www.selenium.dev/documentation/webdriver/
[RestAssured]: https://img.shields.io/badge/RestAssured-%234CAF50.svg?style=for-the-badge&logoColor=white
[RestAssured-url]: https://rest-assured.io/
[TestNG]: https://img.shields.io/badge/TestNg-FF7F00?style=for-the-badge&logo=testng&logoColor=white
[TestNG-url]: https://testng.org/
[SLF4J]: https://img.shields.io/badge/SLF4J-5A5A5A?style=for-the-badge&logo=java&logoColor=white
[SLF4J-url]: https://www.slf4j.org/
[Logback]: https://img.shields.io/badge/Logback-2980B9?style=for-the-badge&logo=java&logoColor=white
[Logback-url]: https://logback.qos.ch/
[JSON]: https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white
[JSON-url]: https://www.json.org/
