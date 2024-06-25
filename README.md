[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/e99KNix9)







# Check4Scam - Share scam information with others

## Yaniv Gabay - 205745615

## Shahar Asher - XXXXXXXXX

## Introduction

Check4Scam is a web application that allows users to share scam information with others. Users can report a scam, view reported scams, so we can prevent scams together.


## Description

This project is a web-based application that allows users to report and view scams. It leverages Spring Boot for the backend, Spring Security for authentication, and Thymeleaf for server-side rendering. The application provides functionalities such as user registration, scam reporting, and administrative controls.

## Features

- **User Authentication:** Login and registration functionality using custom user details service.
- **Scam Reporting:** Users can report scams, view scams they've reported, and view all scams in a feed.
- **Role-Based Access Control:** Differentiates access between regular users and administrators.
- **Real-Time Updates:** Utilizes WebSocket for real-time updates of scam reports.
- **Data Validation:** Implements validation for user and scam report data submission.

## Technologies Used

- Spring Boot
- Spring Security
- Thymeleaf
- Bootstrap
- JPA (Java Persistence API)
- WebSocket
- Xaamp for apache and mysql
- Maven


## Configuration Files

- **MessageConfig:** Configuration for message sources to support internationalization.
- **PasswordConfig:** Sets up the password encoder for Spring Security.
- **SecurityConfig:** Configures Spring Security for authentication and authorization.
- **WebSocketConfig:** Sets up WebSocket message handling.

## Controllers

- **HomeController:** Manages routes for the home page and static content.
- **RegistrationController:** Handles user registration functionalities.
- **ScamReportController:** Manages all scam report related functionalities.
- **WebController:** Manages routing for static pages.
- **WebSocketController:** Handles WebSocket message handling.

## Services

- **CustomUserDetailsService:** Implements the UserDetailsService interface for custom user authentication.
- **ScamReportService:** Manages scam report data and operations.
- **UserService:** Manages user data and operations.
  
## Dto - Data Transfer Object

- **RegistrationForm:** Represents the data transfer object for user registration.
- **TestRegistrationForm:** Represents the data transfer object for user registration testing (no validation).

## Repositories

- **ScamReportRepository:** Handles database operations for scam reports.
- **UserRepository:** Handles database operations for users.

## Models

- **ScamReport:** Represents a scam report entity.
- **User:** Represents a user entity.

## Html Files

```plaintext
├── 403.html
├── common.html
├── error.html
├── index.html
├── login.html
├── signup.html
├── success.html
├── tree_25_6_24
│
├── admin
│   └── admin.html
│
├── scam-reports
│   ├── common.html
│   ├── create.html
│   ├── feed.html
│   ├── likedPosts.html
│   └── manage.html
│
└── user
```
