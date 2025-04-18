# Amazon Automation Test Framework

This is a Selenium WebDriver based test automation framework for testing Amazon's website functionality.

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browser installed
- IDE (Eclipse/IntelliJ)

## Project Structure

```
src/
├── main/java/
│   ├── base/         # Base test class
│   ├── pages/        # Page object classes
│   └── utils/        # Utility classes
└── test/java/
    └── tests/        # Test classes
└── test/resources/
    └── testng.xml    # TestNG configuration
```

## Setup

1. Clone the repository
2. Import as Maven project in your IDE
3. Update `config.properties` with your credentials if needed
4. Run `mvn clean install` to download dependencies

## Running Tests

### From IDE
1. Right-click on `testng.xml`
2. Select "Run As" > "TestNG Suite"

### From Command Line
```bash
mvn clean test
```

### Running Specific Tests
```bash
mvn test -Dtest=LoginTests
mvn test -Dtest=SearchAndFilter
mvn test -Dtest=Pagination
```

## Reports

Test reports are generated in:
- Extent Reports: `test-output/extent-reports/`
- TestNG Reports: `test-output/`

## Features

- Multi-browser support (Chrome, Firefox, Edge)
- Page Object Model design pattern
- Extent Reports for detailed test reporting
- Thread-safe WebDriver management
- Reusable utility methods
- Data-driven testing using JSON 