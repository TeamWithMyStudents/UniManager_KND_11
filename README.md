# UniManager KND-11

UniManager is a console-based university management system designed to handle student and teacher data efficiently. It
provides a structured way to manage academic records, group assignments, and departmental information.

## 🚀 Features

### Student Management

- **Add Students**: Register new students with details (Name, Surname, Group, Email).
- **View All Students**: Display a comprehensive list of all registered students.
- **Delete Students**: Remove student records using their unique ID.
- **Head Student Assignment**: Designate a specific student as the Head Student for their group.
- **Group Search**: Filter and find students based on their group name.

### Teacher Management

- **Add Teachers**: Register new faculty members with department, degree, and salary information.
- **View All Teachers**: List all teachers in the system.
- **Budget Calculation**: Automatically calculate the total salary budget for all teachers.
- **Filter by Degree**: Search for teachers based on their academic degree.

### Core Functionality

- **Input Validation**: Robust validation for emails, passwords, and required fields.
- **Data Normalization**: Ensures consistent formatting for names and other text inputs.
- **Console Interface**: Interactive and user-friendly menu-driven navigation.

## 🛠 Tech Stack

- **Language**: Java 21
- **Build Tool**: Maven
- **Architecture**: MVC (Model-View-Controller) pattern with Service layer.

## 📂 Project Structure

- `src/main/java/ua/knd11/model`: Data models (User, Student, Teacher, Grade, Lesson).
- `src/main/java/ua/knd11/service`: Business logic interfaces and implementations.
- `src/main/java/ua/knd11/controller`: Request handling and coordination between View and Service.
- `src/main/java/ua/knd11/viewer`: Console-based user interface (`ConsoleMenu`).
- `src/main/java/ua/knd11/util`: Utility classes for validation and normalization.