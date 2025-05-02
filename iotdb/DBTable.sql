SET SCHEMA APP;

DROP TABLE CartItem;
DROP TABLE Payment;
DROP TABLE OrderProduct;
DROP TABLE Orders;
DROP TABLE Product;
DROP TABLE Staff;
DROP TABLE Users;

-- USERS Table (used by customers and staff)
CREATE TABLE Users (
    userID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    gender VARCHAR(10),
    mobile VARCHAR(15),
    favoriteColor VARCHAR(30)
);

-- STAFF Table
CREATE TABLE Staff (
    staffID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    position VARCHAR(50),
    address VARCHAR(255)
);

-- PRODUCT Table (IoT devices)
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
    userID INT,
    staffID INT,
    orderDate DATE,
    status VARCHAR(50),
    FOREIGN KEY (userID) REFERENCES Users(userID),
    FOREIGN KEY (staffID) REFERENCES Staff(staffID)
);

-- ORDERPRODUCT Table (linking products to orders)
CREATE TABLE OrderProduct (
    orderProductID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    orderID INT,
    productID INT,
    quantity INT,
    unitPrice DECIMAL(10,2),
    FOREIGN KEY (orderID) REFERENCES Orders(orderID),
    FOREIGN KEY (productID) REFERENCES Product(productID)
);

-- PAYMENT Table
CREATE TABLE Payment (
    paymentID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    orderID INT,
    paymentMethod VARCHAR(50),
    paymentDate DATE,
    amount DECIMAL(10,2),
    status VARCHAR(50),
    FOREIGN KEY (orderID) REFERENCES Orders(orderID)
);

-- CARTITEM Table (for pre-order functionality)
CREATE TABLE CartItem (
    cartItemID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customerID INT,
    productID INT,
    quantity INT,
    dateAdded DATE,
    FOREIGN KEY (customerID) REFERENCES Users(userID),
    FOREIGN KEY (productID) REFERENCES Product(productID)
);

-- Sample USERS
INSERT INTO Users (name, email, password, gender, mobile, favoriteColor)
VALUES 
('Alice Smith', 'alice@example.com', 'pass123', 'Female', '0412345678', 'Blue'),
('Bob Lee', 'bob@example.com', 'securepass', 'Male', '0423456789', 'Green');

-- Sample STAFF
INSERT INTO Staff (firstName, lastName, email, password, position, address)
VALUES 
('John', 'Doe', 'jdoe@iotbay.com', 'adminpass', 'Manager', '123 Admin St'),
('Jane', 'Brown', 'jbrown@iotbay.com', 'staffpass', 'Salesperson', '456 Sales Rd');

-- Sample PRODUCTS
INSERT INTO Product (name, description, price, stockQuantity, category, supplierID)
VALUES 
('Temperature Sensor', 'Measures temperature', 25.99, 50, 'Sensor', 1),
('Motion Detector', 'Detects motion in range', 45.50, 30, 'Sensor', 2);

-- Sample ORDERS
INSERT INTO Orders (userID, staffID, orderDate, status)
VALUES 
(1, 1, DATE('2025-05-01'), 'Pending'),
(2, 2, DATE('2025-05-02'), 'Confirmed');

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
INSERT INTO CartItem (customerID, productID, quantity, dateAdded)
VALUES 
(1, 1, 1, DATE('2025-04-30')),
(2, 2, 2, DATE('2025-04-29'));
