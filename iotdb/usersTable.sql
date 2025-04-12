CREATE TABLE USERS (
    email VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    password VARCHAR(50),
    gender VARCHAR(10),
    favoritecolor VARCHAR(30)
);

-- Insert sample users
INSERT INTO USERS VALUES 
('a@example.com', 'Alice', 'pass123', 'Female', 'Blue'),
('b@example.com', 'Bob', 'pass456', 'Male', 'Green'),
('c@example.com', 'Charlie', 'pass789', 'Other', 'Red');