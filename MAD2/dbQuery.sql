-- Create Users Table
CREATE TABLE Users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    role TEXT CHECK(role IN ('Administrator', 'Signing User', 'Guest')) NOT NULL,
    profile_image BLOB
);

-- Create Branches Table
CREATE TABLE Branches (
    branch_id INTEGER PRIMARY KEY AUTOINCREMENT,
    branch_name TEXT NOT NULL,
    branch_code TEXT NOT NULL UNIQUE,
    location TEXT
);

-- Create Courses Table
CREATE TABLE Courses (
    course_id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_name TEXT NOT NULL,
    faculty TEXT CHECK(faculty IN ('School Of Computing', 'School Of Business', 'School Of Design', 'School Of Languages')) NOT NULL,
    commencement_date DATE NOT NULL,
    duration TEXT NOT NULL,
    method TEXT CHECK(method IN ('Part-Time', 'Full-Time')) NOT NULL,
    campus TEXT NOT NULL,
    branch_id INTEGER NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES Branches(branch_id)
);

-- Create Enrollments Table
CREATE TABLE Enrollments (
    enrollment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    course_id INTEGER NOT NULL,
    discount_code TEXT,
    enrollment_date DATE NOT NULL DEFAULT CURRENT_DATE,
    confirmation_email_sent BOOLEAN NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

-- Create Notifications Table
CREATE TABLE Notifications (
    notification_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    message TEXT NOT NULL,
    sent_date DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Create Version Control Table
CREATE TABLE VersionControl (
    version_id INTEGER PRIMARY KEY AUTOINCREMENT,
    change_description TEXT NOT NULL,
    changed_by INTEGER NOT NULL,
    change_date DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (changed_by) REFERENCES Users(user_id)
);

-- Create Locations Table
CREATE TABLE Locations (
    location_id INTEGER PRIMARY KEY AUTOINCREMENT,
    branch_id INTEGER NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES Branches(branch_id)
);

-- Insert Mock Data into Users Table
INSERT INTO Users (username, password, email, role) VALUES 
('admin', 'admin123', 'admin@nibm.lk', 'Administrator'),
('sandeep', 'pass123', 'sandeep@gmail.com', 'Signing User'),
('kavindu', '12345', 'kavindu@gmail.com', 'Signing User'),
('guest1', 'guestpass', 'guest1@example.com', 'Guest');

-- Insert Mock Data into Branches Table
INSERT INTO Branches (branch_name, branch_code, location) VALUES 
('Colombo Branch', 'CMB01', 'Colombo 7, Sri Lanka'),
('Kandy Branch', 'KDY02', 'Kandy City Center, Sri Lanka'),
('Galle Branch', 'GLL03', 'Galle Fort, Sri Lanka');

-- Insert Mock Data into Courses Table
INSERT INTO Courses (course_name, faculty, commencement_date, duration, method, campus, branch_id) VALUES 
('Diploma in Computing', 'School Of Computing', '2024-02-01', '6 months', 'Full-Time', 'Colombo', 1),
('Certificate in Business Management', 'School Of Business', '2024-03-15', '3 months', 'Part-Time', 'Kandy', 2),
('Graphic Design Fundamentals', 'School Of Design', '2024-04-01', '4 months', 'Full-Time', 'Galle', 3),
('English for Beginners', 'School Of Languages', '2024-05-20', '2 months', 'Part-Time', 'Colombo', 1);

-- Insert Mock Data into Enrollments Table
INSERT INTO Enrollments (user_id, course_id, discount_code, confirmation_email_sent) VALUES 
(2, 1, 'DISCOUNT20', 1),
(3, 2, 'NEWYEAR10', 0),
(3, 4, NULL, 1);

-- Insert Mock Data into Notifications Table
INSERT INTO Notifications (user_id, message) VALUES 
(2, 'Your enrollment for Diploma in Computing has been confirmed.'),
(3, 'Reminder: Complete your registration for Graphic Design Fundamentals.');

-- Insert Mock Data into Version Control Table
INSERT INTO VersionControl (change_description, changed_by) VALUES 
('Initial database creation', 1),
('Added new course: Graphic Design Fundamentals', 1);

-- Insert Mock Data into Locations Table
INSERT INTO Locations (branch_id, latitude, longitude) VALUES 
(1, 6.927079, 79.861244), -- Colombo
(2, 7.290572, 80.633728), -- Kandy
(3, 6.032596, 80.217803); -- Galle
