# Student Management Application

A Spring Boot web application for managing student records with authentication.

## Features
- Student CRUD operations
- User authentication and authorization
- Responsive web interface
- H2/MySQL database support

## Local Development

### Prerequisites
- Java 17
- Maven

### Running Locally
```bash
./mvnw spring-boot:run
```
Access at: http://localhost:8080

## Deployment to Railway

### Option 1: Using Railway (Recommended - Free tier available)

1. **Create a Railway account** at https://railway.app

2. **Connect your GitHub repository**:
   - Push this code to a GitHub repository
   - Connect Railway to your GitHub account

3. **Deploy the application**:
   - Click "New Project" → "Deploy from GitHub repo"
   - Select your repository
   - Railway will automatically detect it's a Spring Boot app

4. **Add MySQL Database**:
   - In your Railway project, add a MySQL database
   - Copy the database URL from Railway dashboard
   - Add these environment variables in Railway:
     ```
     DATABASE_URL=mysql://user:password@host:port/database
     DB_USERNAME=your_db_username
     DB_PASSWORD=your_db_password
     DB_DRIVER=com.mysql.cj.jdbc.Driver
     DB_DIALECT=org.hibernate.dialect.MySQLDialect
     H2_CONSOLE_ENABLED=false
     ```

5. **Access your website**:
   - Railway will provide a public URL (e.g., `https://your-app.railway.app`)

### Option 2: Using Render (Alternative free option)

1. **Create a Render account** at https://render.com

2. **Connect your repository** and create a new Web Service

3. **Configure build settings**:
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -jar target/*.jar`

4. **Add environment variables** (same as Railway)

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `PORT` | Server port | 8080 |
| `DATABASE_URL` | Database connection URL | H2 file database |
| `DB_USERNAME` | Database username | (empty) |
| `DB_PASSWORD` | Database password | (empty) |
| `DB_DRIVER` | JDBC driver class | H2 driver |
| `DB_DIALECT` | Hibernate dialect | H2 dialect |
| `H2_CONSOLE_ENABLED` | Enable H2 console | true |

## API Endpoints

- `GET /` - Home page
- `GET /login` - Login page
- `GET /signup` - Registration page
- `GET /students` - List all students
- `POST /students` - Add new student
- `GET /students/{id}` - View student details
- `PUT /students/{id}` - Update student
- `DELETE /students/{id}` - Delete student