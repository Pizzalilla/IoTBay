DROP TABLE Log;
DROP TABLE CartItem;
DROP TABLE Payment;
DROP TABLE OrderProduct;
DROP TABLE Product;
DROP TABLE Users;



-- Unified USERS Table
CREATE TABLE Users (
    userID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    gender VARCHAR(10),
    mobile VARCHAR(15),
    address VARCHAR(255),
    type VARCHAR(20),     -- 'customer' or 'staff'
    position VARCHAR(50), -- Only for staff; nullable for customers
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE Log (
    id VARCHAR(36) PRIMARY KEY,
    userId INT,
    loginTime TIMESTAMP,
    logoutTime TIMESTAMP
);

-- DEVICE Table
CREATE TABLE Device (
    deviceId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    deviceName VARCHAR(255),
    price DECIMAL(10,2),
    type VARCHAR(100),
    stockQty INT,
    imageName VARCHAR(255)
);

-- PRODUCT Table
CREATE TABLE Product (
    productID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100),
    description VARCHAR(255),
    price DECIMAL(10,2),
    stockQuantity INT,
    category VARCHAR(50),
    supplierID INT
);

-- ORDERS Table
CREATE TABLE Orders (
    orderID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customerID INT,
    staffID INT,
    orderDate DATE,
    status VARCHAR(50)
);

-- ORDERPRODUCT Table
CREATE TABLE OrderProduct (
    orderProductID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    orderID INT,
    productID INT,
    quantity INT,
    unitPrice DECIMAL(10,2)
);

-- PAYMENT Table
CREATE TABLE Payment (
    paymentID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    orderID INT,
    paymentMethod VARCHAR(50),
    paymentDate DATE,
    amount DECIMAL(10,2),
    status VARCHAR(50)
);

-- CARTITEM Table
CREATE TABLE CartItem (
    cartItemID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    userID INT,
    productID INT,
    quantity INT,
    dateAdded DATE
);

-- Sample DEVICES
INSERT INTO Device (deviceName, price, type, stockQty, imageName) VALUES
    ('Temperature Sensor', 25.99, 'Sensor', 50, 'temp_sensor.png'),
    ('Motion Detector', 45.50, 'Sensor', 30, 'motion_detector.png');


-- Sample USERS (customers and staff)
INSERT INTO Users (firstName, lastName, email, password, gender, mobile, address, type, position)
VALUES 
('Alice', 'Smith', 'alice@example.com', 'pass123', 'Female', '0412345678', '789 Customer Rd', 'customer', NULL),
('Bob', 'Lee', 'bob@example.com', 'securepass', 'Male', '0423456789', '321 User Ave', 'customer', NULL),
('John', 'Doe', 'jdoe@iotbay.com', 'adminpass', NULL, NULL, '123 Admin St', 'staff', 'Manager'),
('Charlie', 'Nguyen', 'charlie.nguyen@example.com', 'charliepass', 'Male', '0434567890', '987 New St', 'customer', NULL),
('Jane', 'Brown', 'jbrown@iotbay.com', 'staffpass', NULL, NULL, '456 Sales Rd', 'staff', 'Salesperson');

-- Sample PRODUCTS
INSERT INTO Product (name, description, price, stockQuantity, category, supplierID)
VALUES 
('Temperature Sensor', 'Measures temperature', 25.99, 50, 'Sensor', 1),
('Motion Detector', 'Detects motion in range', 45.50, 30, 'Sensor', 2);

-- Sample ORDERS
INSERT INTO Orders (customerID, staffID, orderDate, status)
VALUES 
(1, 3, DATE('2025-05-01'), 'Pending'),
(2, 4, DATE('2025-05-02'), 'Confirmed');

-- Sample ORDERPRODUCTS
INSERT INTO OrderProduct (orderID, productID, quantity, unitPrice)
VALUES 
(1, 1, 2, 25.99),
(2, 2, 1, 45.50);

-- Sample PAYMENTS
INSERT INTO Payment (orderID, paymentMethod, paymentDate, amount, status)
VALUES 
(1, 'Credit Card', DATE('2025-05-01'), 51.98, 'Completed'),
(2, 'PayPal', DATE('2025-05-02'), 45.50, 'Pending');

-- Sample CARTITEMS
INSERT INTO CartItem (userID, productID, quantity, dateAdded)
VALUES 
(1, 1, 1, DATE('2025-04-30')),
(2, 2, 2, DATE('2025-04-29'));
