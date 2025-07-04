CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
);


CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    year_of_study INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


CREATE TABLE professors (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    department VARCHAR(100) NOT NULL,
    professor_number VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_code VARCHAR(10) UNIQUE NOT NULL,
    professor_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professors(id) ON DELETE SET NULL
);


CREATE TABLE enrollments (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);


CREATE TABLE grades (
    id SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade DECIMAL(3,1) CHECK (grade BETWEEN 0 AND 10),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);


CREATE TABLE classes (
    id SERIAL PRIMARY KEY,
    class_name VARCHAR(100) NOT NULL,
    course_id INT NOT NULL,
    schedule TIMESTAMP NOT NULL,
    location VARCHAR(100),
    class_type VARCHAR(50),
    duration INT,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);


CREATE TABLE assignments (
    id SERIAL PRIMARY KEY,
    course_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    due_date TIMESTAMP NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);


CREATE TABLE announcements(
    id SERIAL PRIMARY KEY,
    course_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);


CREATE TABLE notifications(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);


CREATE TABLE submissions (
    id SERIAL PRIMARY KEY,
    assignment_id INT NOT NULL,
    student_id INT NOT NULL,
    submitted_at TIMESTAMP NOT NULL,
    content TEXT,
    grade DECIMAL(3,1) CHECK (grade BETWEEN 0 AND 10),
    feedback TEXT,
    status VARCHAR(20) DEFAULT 'Draft',
    FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);


CREATE TABLE student_starting (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    address VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    gpa_transcript VARCHAR(100) NOT NULL,
    ethnicity  OPTION("Shqpitar" , "Serb" , "Boshnjak" , "Romë" , "Ashkali" , "Egjiptian") NOT NULL,
    extra_credit_document VARCHAR(100) NOT NULL,
    test_score DECIMAL(100) NOT NULL,
    acceptance_test_score INT CHECK (acceptance_test_score BETWEEN 0 AND 20) NOT NULL,
    program OPTION("IKS" , "EAR" , "EEN" , "TIK") NOT NULL,
);

CREATE TABLE student_rejected (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    address VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    gpa_transcript VARCHAR(100) NOT NULL,
    ethnicity  OPTION("Shqpitar" , "Serb" , "Boshnjak" , "Romë" , "Ashkali" , "Egjiptian") NOT NULL,
    extra_credit_document VARCHAR(100) NOT NULL,
    test_score DECIMAL(100) NOT NULL,
    acceptance_test_score INT CHECK (acceptance_test_score BETWEEN 0 AND 20) NOT NULL,
    program OPTION("IKS" , "EAR" , "EEN" , "TIK") NOT NULL
);

CREATE TABLE student_accepted (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    address VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    status OPTION("I rregullt" , "I parregullt" , "Korespodencë") NOT NULL,
    gpa DOUBLE NOT NULL,
    program VARCHAR(255) NOT NULL
);

CREATE TABLE requests (
    id INT PRIMARY KEY NOT NULL,
    student_id INT NOT NULL,
    request_time TIMESTAMP WITHOUT TIME ZONE,
    accepted BOOLEAN,
    repeat BOOLEAN,
    FOREIGN KEY (student_id) REFERENCES student_starting(id)
);

CREATE TABLE iks (
    id SERIAL PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seasts INT,
    full BOOLEAN,
    FOREIGN KEY (AcceptedID) REFERENCES student_accepted(id)
);

CREATE TABLE ear (
    id SERIAL PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seats INT,
    full BOOLEAN,
    FOREIGN KEY (accepted_id) REFERENCES student_accepted(id)
);

CREATE TABLE een (
    id INT PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seats INT,
    full BOOLEAN,
    FOREIGN KEY (accepted_id) REFERENCES student_accepted(id)
);

CREATE TABLE tik (
    id INT PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seats INT,
    full BOOLEAN,
    FOREIGN KEY (accepted_id) REFERENCES student_accepted(id)
);

CREATE TABLE transfer_request (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    status OPTION("I rregullt" , "I parregullt" , "Korespodencë") NOT NULL,
    gpa DOUBLE NOT NULL,
    program VARCHAR(255) NOT NULL,
    targetprogram OPTION("IKS" , "EAR" , "EEN" , "TIK") NOT NULL,
    FOREIGN KEY (id) REFERENCES student_accepted(id),
    FOREIGN KEY (name) REFERENCES student_accepted(name),
    FOREIGN KEY (surname) REFERENCES student_accepted(surname),
    FOREIGN KEY (status) REFERENCES student_accepted(status),
    FOREIGN KEY (program) REFERENCES student_accepted(program),
    FOREIGN KEY (gpa) REFERENCES student_accepted(gpa)
    FOREIGN KEY (accepted_id) REFERENCES student_accepted(id)
);

CREATE TABLE tik (
    id INT PRIMARY KEY,
    accepted_id VARCHAR(50) NOT NULL,
    available_seats INT,
    full BOOLEAN,
    FOREIGN KEY (accepted_id) REFERENCES student_accepted(id)
);

CREATE TABLE transfer_request (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    name VARCHAR(20) NOT NULL,
    surname VARCHAR(20) NOT NULL,
    status OPTION("I rregullt" , "I parregullt" , "Korespodencë") NOT NULL,
    program VARCHAR(255) NOT NULL,
    targetprogram OPTION("IKS" , "EAR" , "EEN" , "TIK") NOT NULL,
    FOREIGN KEY (id) REFERENCES student_accepted(id),
    FOREIGN KEY (name) REFERENCES student_accepted(name),
    FOREIGN KEY (surname) REFERENCES student_accepted(surname),
    FOREIGN KEY (status) REFERENCES student_accepted(status),
    FOREIGN KEY (program) REFERENCES student_accepted(program),
);
