# Spring Boot Project: Travel App API

## Overview
This Spring Boot Project is done for Unite Testing purpose.
It utilizes MongoDB for data storage, offers a comprehensive backend solution designed for managing user profiles, trips, flights, travel plans, bookings, airports, ticket purchases, and trip feedback. Built with Gradle, it provides a modern, efficient, and scalable approach to backend services.


# Unit Testing and Coverage
This project is designed to achieve 100% test coverage for all service and controller classes. The unit tests ensure that all classes, methods, lines, and branches are thoroughly tested.
The project has 114 unite testings, 44 Unite Testings for Controllers, 61 Unite Testings for Services, and 9 Unite Testings for Exceptions.
## Running Unit Tests
To run the unit tests and check coverage, follow these steps:

Run the Tests
Execute the following command to run all tests:
### More Run/Debug
### Run 'Tests in 'com.example.helloworld" with coverage.
 Test Coverage Details
Controller Tests: Achieve 100% coverage in class, methods, lines, and branches.
Service Tests: Achieve 100% coverage in class, methods, lines, and branches.
Exception Tests: Includes tests for exceptions with 100% coverage.
This project ensures comprehensive testing with the following coverage results:

Controller Tests: 100% coverage
Service Tests: 100% coverage
Exception Tests: 100% coverage
Based on the professor requirements which were that we should cover all the possible unite testings for:Controllers, Services (I also added the unite testings for Exceptions) the repository and pojo are not covered.
Screenshots of the coverage reports for service and controller tests showing 100% coverage are included below:
Controller Test Coverage
![Screenshot 2024-05-17 232952](https://github.com/BesartaTefiki/Travel-App-API-Unite-Tests/assets/106693895/c712fc68-efbb-4651-8429-cb1fb6da4dee)

Service Test Coverage
![Screenshot 2024-05-17 233357](https://github.com/BesartaTefiki/Travel-App-API-Unite-Tests/assets/106693895/c3ca5d74-14e5-4ab7-bab4-818ed7c58edb)

Exception Test Coverage
![Screenshot 2024-05-18 011658](https://github.com/BesartaTefiki/Travel-App-API-Unite-Tests/assets/106693895/990382ec-fa37-4c25-b1e2-ba938732ed2e)

By following these steps and utilizing the provided configurations, you can ensure that your Spring Boot project is fully tested and meets the requirements for unit testing coverage.
### Prerequisites
- JDK 11 or newer
- MongoDB 4.4 or newer
- Gradle 6.3 or newer (with Kotlin DSL support)
- An IDE supporting Gradle and Kotlin (e.g., IntelliJ IDEA)

### Installation Steps
1. **Clone the Repository**  
   Clone the project repository from GitHub and navigate into the project directory:  
`git clone https://github.com/BesartaTefiki/Travel-App-API-Unite-Tests.git`

`cd java-project-springboot`


3. **MongoDB Setup**  
Ensure MongoDB is installed and running on your system. You may need to create a new database for your project:  
`mongo`
`use springboot_project`


3. **Configure the Application**  
Update the src/main/resources/application.properties file (or application.yml if you prefer YAML) with your MongoDB connection details. For a typical local setup, the configuration might look like this:

`spring:`
`data:`
`mongodb:`
`uri: mongodb://localhost:27017/springboot_project`

5. **Build the Project with Gradle**  
In the project root directory, execute the following command to build the project:  
`./gradlew build`


6. **Run the Application**  
Start the Spring Boot application using Gradle:  
`./gradlew bootRun`


7. **Verify Installation**  
Once the application is running, you can verify its status by accessing the actuator health endpoint:  
[GET http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

