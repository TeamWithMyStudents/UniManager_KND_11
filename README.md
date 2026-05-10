# UniManager KND-11

UniManager is a currently in development console-based university management system designed to handle student and
teacher data efficiently. It
provides a structured way to manage academic records, group assignments, and departmental information with secure
password handling and robust validation.

## Features

### Student Management

- **Add Students**: Register new students with details (Name, Surname, Group, Email, Password).
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

- **Secure Password Storage**: Passwords are hashed using Argon2id with salt and pepper for enhanced security.
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
- **Security**: Password4j library for Argon2id password hashing
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

### Database Storage Format (PostgreSQL)

```text
Student, Name, Surname, Group, ROLE, Email@example.com, hashed_password, salt
Teacher, Name, Surname, Department, Degree, Salary, Email@example.com, hashed_password, salt
```

## Password Security

- **Algorithm**: Argon2id
- **Memory**: 19456 KB (≈19 MiB, OWASP baseline)
- **Iterations**: 2
- **Parallelism**: 1
- **Key Length**: 128 bits
- **Salt & Pepper**: Additional security layers with unique salts and application-wide pepper
- **Storage**: Passwords are never stored in plain text - only hashed values are persisted

## Validation Rules

- **Email**: Standard email format validation (1-254 characters, 1-64 before @)
- **Password**: Minimum 8 characters, allowed: English letters, digits, -!@#$%^&*.
- **Names**: English/Ukrainian letters, spaces, apostrophes, hyphens (1-100 characters)
- **Groups**: English/Ukrainian letters, digits, underscores, hyphens (1-100 characters)

## Configuration

### DB url and superuser `(admin)`

DB url and superuser can be configured in `.env` (must be placed in project root):

- `DATABASE_URL`:
- `SUPER_USER_EMAIL`:
- `SUPER_USER_PASSWORD`:

### Password Hashing

Password hashing behavior can be configured in `src/main/resources/psw4j.properties`:

- `hash.argon2.memory`: Memory usage in KB (default: 19456)
- `hash.argon2.iterations`: Number of iterations (default: 2)
- `hash.argon2.parallelism`: Parallelism factor (default: 1)
- `hash.argon2.length`: Key length in bits (default: 128)

STATIC_PEPPER (pepper value) and RANDOM_SALT_LENGTH (salt length) for password protection can be configured in `.env`:

- `STATIC_PEPPER`: Application-wide pepper value
- `RANDOM_SALT_LENGTH`: Length of random salt in bytes

## Setting Up the Application

1. Install Java Development Kit (JDK) 25.
2. Install Apache Maven.
3. Clone the repository to your local machine.
4. Create a new file named `.env` in the root directory of the project.
5. Inside the `.env` file, add the following lines:

- `DATABASE_URL`=YOUR_REMOTE_DB_URL
- `SUPER_USER_EMAIL`=YOUR_SUPER_USER_EMAIL
- `SUPER_USER_PASSWORD`=YOUR_SUPER_USER_PASSWORD
- `STATIC_PEPPER`=YOUR_PEPPER
- `RANDOM_SALT_LENGTH`=YOUR_SALT_LENGTH
## WIP

- Website control panel deployment
- Improvements to the user interface
- Improve constants handling and usage