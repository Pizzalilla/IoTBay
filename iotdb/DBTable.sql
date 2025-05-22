-- Remove unsupported PRAGMA (not valid in Derby)
-- Removed DROP TABLE IF EXISTS statements (not supported in Derby)

-- USERS Table (used by customers and staff)
CREATE TABLE Users (
    customerID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    gender VARCHAR(20),
    mobile VARCHAR(20),
    favoriteColor VARCHAR(50)
);

-- STAFF Table
CREATE TABLE Staff (
    staffID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    firstName VARCHAR(100),
    lastName VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    position VARCHAR(100),
    address VARCHAR(255)
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

-- ORDERS Table
CREATE TABLE Orders (
    orderID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customerID INT,
    staffID INT,
    orderDate VARCHAR(50),
    status VARCHAR(50),
    FOREIGN KEY (customerID) REFERENCES Users(customerID),
    FOREIGN KEY (staffID) REFERENCES Staff(staffID)
);

-- ORDERPRODUCT Table (linking devices to orders)
CREATE TABLE OrderProduct (
    orderProductID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    orderID INT,
    deviceId INT,
    quantity INT,
    unitPrice DECIMAL(10,2),
    FOREIGN KEY (orderID) REFERENCES Orders(orderID),
    FOREIGN KEY (deviceId) REFERENCES Device(deviceId)
);

-- PAYMENT Table
CREATE TABLE Payment (
    paymentID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    orderID INT,
    paymentMethod VARCHAR(100),
    paymentDate VARCHAR(50),
    amount DECIMAL(10,2),
    status VARCHAR(50),
    FOREIGN KEY (orderID) REFERENCES Orders(orderID)
);

-- CARTITEM Table (for pre-order functionality)
CREATE TABLE CartItem (
    cartItemID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    customerID INT,
    deviceId INT,
    quantity INT,
    dateAdded VARCHAR(50),
    FOREIGN KEY (customerID) REFERENCES Users(customerID),
    FOREIGN KEY (deviceId) REFERENCES Device(deviceId)
);

-- Sample USERS
INSERT INTO Users (name, email, password, gender, mobile, favoriteColor) VALUES
    ('Alice Smith', 'alice@example.com', 'pass123', 'Female', '0412345678', 'Blue'),
    ('Bob Lee', 'bob@example.com', 'securepass', 'Male', '0423456789', 'Green');

-- Sample STAFF
INSERT INTO Staff (firstName, lastName, email, password, position, address) VALUES
    ('John', 'Doe', 'jdoe@iotbay.com', 'adminpass', 'Manager', '123 Admin St'),
    ('Jane', 'Brown', 'jbrown@iotbay.com', 'staffpass', 'Salesperson', '456 Sales Rd');

-- Sample DEVICES
INSERT INTO Device (deviceName, price, type, stockQty, imageName) VALUES
    ('Temperature Sensor', 25.99, 'Sensor', 50, 'temp_sensor.png'),
    ('Motion Detector', 45.50, 'Sensor', 30, 'motion_detector.png');

-- Sample ORDERS
INSERT INTO Orders (customerID, staffID, orderDate, status) VALUES
    (1, 1, '2025-05-01', 'Pending'),
    (2, 2, '2025-05-02', 'Confirmed');

-- Sample ORDERPRODUCTS
INSERT INTO OrderProduct (orderID, deviceId, quantity, unitPrice) VALUES
    (1, 1, 2, 25.99),
    (2, 2, 1, 45.50);

-- Sample PAYMENTS
INSERT INTO Payment (orderID, paymentMethod, paymentDate, amount, status) VALUES
    (1, 'Credit Card', '2025-05-01', 51.98, 'Completed'),
    (2, 'PayPal', '2025-05-02', 45.50, 'Pending');

-- Sample CARTITEMS
INSERT INTO CartItem (customerID, deviceId, quantity, dateAdded) VALUES
    (1, 1, 1, '2025-04-30'),
    (2, 2, 2, '2025-04-29');
