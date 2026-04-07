# UniManager KND-11

UniManager is a console-based university management system designed to handle student and teacher data efficiently. It
provides a structured way to manage academic records, group assignments, and departmental information with secure
password handling and robust validation.

## Features

### Student Management

- **Add Students**: Register new students with details (Name, Surname, Lastname, Group, Email, Password).
- **View All Students**: Display a comprehensive list of all registered students.
- **Delete Students**: Remove student records using their unique ID.
- **Head Student Assignment**: Designate a specific student as the Head Student for their group.
- **Group Search**: Filter and find students based on their group name.

### Teacher Management

- **Add Teachers**: Register new faculty members with department, degree, and salary information.
- **View All Teachers**: List all teachers in the system.
- **Budget Calculation**: Automatically calculate the total salary budget for all teachers.
- **Filter by Degree**: Search for teachers based on their academic degree.

### Authentication & Security

- **Secure Password Storage**: Passwords are hashed using PBKDF2 with salt and pepper for enhanced security.
- **User Authentication**: Login system with email and password verification.
- **Session Management**: Single-user session control to prevent multiple simultaneous logins.
- **Password Validation**: Strong password requirements (8+ characters, allowed special characters).

### Core Functionality

- **Input Validation**: Robust validation for emails, passwords, names, and required fields.
- **Data Normalization**: Ensures consistent formatting for names and other text inputs.
- **Error Handling**: Comprehensive error reporting with specific validation messages.
- **Console Interface**: Interactive and user-friendly menu-driven navigation.

## Tech Stack

- **Language**: Java 25
- **Build Tool**: Maven
- **Security**: Password4j library for PBKDF2 password hashing
- **Architecture**: MVC (Model-View-Controller) pattern with Service layer.

## Project Structure

- `src/main/java/ua/knd11/model`: Data models (User, Student, Teacher).
- `src/main/java/ua/knd11/service`: Business logic interfaces and implementations.
- `src/main/java/ua/knd11/controller`: Request handling and coordination between View and Service.
- `src/main/java/ua/knd11/security`: Authentication and password security.
- `src/main/java/ua/knd11/viewer`: Console-based user interface (`ConsoleMenu`).
- `src/main/java/ua/knd11/util`: Utility classes for validation and file handling.
- `src/main/resources`: Configuration files (psw4j.properties).

## Data Format

### File Storage Format (users_db.txt)

```
Student, Name, Surname, Lastname, Group, ROLE, Email@example.com, hashed_password
Teacher, Name, Surname, Department, Degree, Salary, Email@example.com, hashed_password
```

## Password Security

- **Algorithm**: PBKDF2 with SHA256
- **Iterations**: 310,000
- **Key Length**: 256 bits
- **Salt & Pepper**: Additional security layers with unique salts and application-wide pepper
- **Storage**: Passwords are never stored in plain text - only hashed values are persisted

## Validation Rules

- **Email**: Standard email format validation (1-254 characters, 1-64 before @)
- **Password**: Minimum 8 characters, allowed: English letters, digits, -!@#$%^&*.
- **Names**: English/Ukrainian letters, spaces, apostrophes, hyphens (1-100 characters)
- **Groups**: English/Ukrainian letters, digits, underscores, hyphens (1-100 characters)

## Configuration

Password hashing behavior can be configured in `src/main/resources/psw4j.properties`:

- `hash.pbkdf2.algorithm`: Hashing algorithm (default: SHA256)
- `hash.pbkdf2.iterations`: Number of iterations (default: 310000)
- `hash.pbkdf2.length`: Key length in bits (default: 256)
