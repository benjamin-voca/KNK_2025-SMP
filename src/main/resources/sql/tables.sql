CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('student', 'professor', 'admin') NOT NULL
);


CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    year_of_study INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


CREATE TABLE professors (
    professor_id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    department VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(10) UNIQUE NOT NULL,
    professor_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professors(professor_id) ON DELETE SET NULL
);


CREATE TABLE enrollments (
    enrollment_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);


CREATE TABLE grades (
    grade_id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade DECIMAL(3,1) CHECK (grade BETWEEN 0 AND 10),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);


CREATE TABLE classes (
    class_id SERIAL PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    course_id INT NOT NULL,
    schedule VARCHAR(255) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

CREATE TABLE startingStudent (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    adress VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    gpa DECIMAL(3,2) CHECK (gpa BETWEEN 2 AND 5) NOT NULL,
    ethinicity VARCHAR(50) NOT NULL,
    extraCredits INT NOT NULL,
    testScore DECIMAL(3,2) NOT NULL,
    AceptionScore INT CHECK ( AceptionScore BETWEEN 0 AND 20) NOT NULL,
    Program VARCHAR(50) NOT NULL,
);

CREATE TABLE iks (
    id SERIAL PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seasts INT,
    full BOOLEAN,
    FOREIGN KEY (AcceptedID) REFERENCES studentsAccepted(accepted_id)
);

CREATE TABLE ear (
    id SERIAL PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seats INT,
    full BOOLEAN,
    FOREIGN KEY (accepted_id) REFERENCES studentsAccepted(accepted_id)
);

CREATE TABLE een (
    id INT PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seats INT,
    full BOOLEAN,
    FOREIGN KEY (accepted_id) REFERENCES STUDENTSAccepted(accepted_id)
);
