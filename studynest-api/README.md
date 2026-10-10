# StudyNest - Study Center Seat Booking Portal

StudyNest is a modern academic seat reservation portal built with **Spring Boot 3**, **Java 21**, **Spring Data JPA**, and **Thymeleaf** with Tailwind CSS.

---

## Key Highlights & Architectural Features

1. **Dual Role Authentication (Administrator & Student)**:
   - **Student Access**: Search seats, reserve desks, view personal reservations, check in on arrival, and cancel personal reservations.
   - **Administrator Access**: Comprehensive console overseeing all 20 seats, center occupancy metrics, manual check-in assistance, and administrative cancellation override.

2. **Left Navigation Sidebar**:
   - Fixed navigation sidebar with center branding, active profile card, role indicators, and one-click navigation between the Floor Plan, Personal Reservations, Administrator Console, and Center Guidelines.

3. **Humanized & Professional Interface**:
   - Clean, distraction-free SaaS design with zero emojis.
   - Clear visual status indicators with color-coded badges:
     - **Green (Available)**: Vacant desk ready for immediate reservation.
     - **Red (Occupied)**: Active session confirmed and checked in.
     - **Yellow (Awaiting Arrival)**: Reserved desk holding a 15-minute arrival check-in buffer.

4. **Strict Reservation Ownership & Security**:
   - Server-side authorization ensures **students can only cancel their own reservations**.
   - Attempted cancellations by unauthorized users are rejected with HTTP 403 Forbidden.
   - Administrators hold system override authority.

5. **Persistent Database Storage**:
   - **Development & Local**: File-based H2 database stored under `./data/studynestdb`. Reservations and accounts persist reliably across restarts without losing data.
   - **Production & Cloud**: PostgreSQL driver included for zero-friction cloud deployment on Render, Railway, AWS, or Docker.

---

## Default Demo Accounts

| Role | Identifier / Email | Phone | Password | Privileges |
| :--- | :--- | :--- | :--- | :--- |
| **Administrator** | `admin@studynest.com` | `9999999999` | `admin123` | Center-wide management, override cancel & check-in |
| **Student** | `alex@studynest.com` | `9876543210` | `pass123` | Book seats, check in, cancel own reservations |
| **Student** | `sarah@studynest.com` | `9812345678` | `pass123` | Book seats, check in, cancel own reservations |
| **Student** | `michael@studynest.com` | `9988776655` | `pass123` | Book seats, check in, cancel own reservations |

---

## Running the Application Locally

### 1. Prerequisites:
- Java 21 JDK (`java -version`)

### 2. Launch the Application:
```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS / Linux
./mvnw spring-boot:run
```

### 3. Open in Browser:
- **Application Portal**: [http://localhost:8080](http://localhost:8080)
- **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:file:./data/studynestdb`
  - Username: `sa`
  - Password: *(leave blank)*

---

## REST API Overview

| Method | Route | Description | Authorization |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/students/login` | Authenticate Student or Administrator | Public |
| `POST` | `/api/students/register` | Self-register a new Student account | Public |
| `GET` | `/api/seats/map` | Query 20 seat statuses for target date & slot | Public |
| `POST` | `/api/bookings` | Create desk reservation (up to 7 days advance) | Authenticated User |
| `POST` | `/api/bookings/{id}/check-in` | Confirm student arrival / mark Occupied | Student (Owner) / Admin |
| `POST` | `/api/bookings/{id}/cancel` | Cancel desk reservation | **Student (Owner only) / Admin** |
| `GET` | `/api/bookings/student/{id}` | Retrieve personal reservation history | Student / Admin |
| `GET` | `/api/bookings/admin/all` | Retrieve all center reservations | **Admin only** |
