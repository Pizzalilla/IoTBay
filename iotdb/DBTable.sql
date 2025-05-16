-- Enable foreign key support
PRAGMA foreign_keys = ON;

-- Drop tables if they exist (respecting dependency order)
DROP TABLE IF EXISTS CartItem;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS OrderProduct;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Device;
DROP TABLE IF EXISTS Staff;
DROP TABLE IF EXISTS Users;

-- USERS Table (used by customers and staff)
CREATE TABLE Users (
                       customerID INTEGER PRIMARY KEY AUTOINCREMENT,
                       name TEXT,
                       email TEXT UNIQUE,
                       password TEXT,
                       gender TEXT,
                       mobile TEXT,
                       favoriteColor TEXT
);

-- STAFF Table
CREATE TABLE Staff (
                       staffID INTEGER PRIMARY KEY AUTOINCREMENT,
                       firstName TEXT,
                       lastName TEXT,
                       email TEXT UNIQUE,
                       password TEXT,
                       position TEXT,
                       address TEXT
);

CREATE TABLE Device (
                        deviceId INTEGER PRIMARY KEY AUTOINCREMENT,
                        deviceName TEXT,
                        price NUMERIC,
                        type TEXT,
                        stockQty INTEGER,
                        imageName TEXT
);

-- ORDERS Table
CREATE TABLE Orders (
                        orderID INTEGER PRIMARY KEY AUTOINCREMENT,
                        customerID INTEGER,
                        staffID INTEGER,
                        orderDate TEXT,
                        status TEXT,
                        FOREIGN KEY (customerID) REFERENCES Users(customerID),
                        FOREIGN KEY (staffID) REFERENCES Staff(staffID)
);

-- ORDERPRODUCT Table (linking devices to orders)
CREATE TABLE OrderProduct (
                              orderProductID INTEGER PRIMARY KEY AUTOINCREMENT,
                              orderID INTEGER,
                              deviceId INTEGER,
                              quantity INTEGER,
                              unitPrice NUMERIC,
                              FOREIGN KEY (orderID) REFERENCES Orders(orderID),
                              FOREIGN KEY (deviceId) REFERENCES Device(deviceId)
);

-- PAYMENT Table
CREATE TABLE Payment (
                         paymentID INTEGER PRIMARY KEY AUTOINCREMENT,
                         orderID INTEGER,
                         paymentMethod TEXT,
                         paymentDate TEXT,
                         amount NUMERIC,
                         status TEXT,
                         FOREIGN KEY (orderID) REFERENCES Orders(orderID)
);

-- CARTITEM Table (for pre-order functionality)
CREATE TABLE CartItem (
                          cartItemID INTEGER PRIMARY KEY AUTOINCREMENT,
                          customerID INTEGER,
                          deviceId INTEGER,
                          quantity INTEGER,
                          dateAdded TEXT,
                          FOREIGN KEY (customerID) REFERENCES Users(customerID),
                          FOREIGN KEY (deviceId) REFERENCES Device(deviceId)
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

-- Sample DEVICES
INSERT INTO Device (deviceName, price, type, stockQty, imageName)
VALUES
    ('Temperature Sensor', 25.99, 'Sensor', 50, 'temp_sensor.png'),
    ('Motion Detector', 45.50, 'Sensor', 30, 'motion_detector.png');

-- Sample ORDERS
INSERT INTO Orders (customerID, staffID, orderDate, status)
VALUES
    (1, 1, '2025-05-01', 'Pending'),
    (2, 2, '2025-05-02', 'Confirmed');

-- Sample ORDERPRODUCTS
INSERT INTO OrderProduct (orderID, deviceId, quantity, unitPrice)
VALUES
    (1, 1, 2, 25.99),
    (2, 2, 1, 45.50);

-- Sample PAYMENTS
INSERT INTO Payment (orderID, paymentMethod, paymentDate, amount, status)
VALUES
    (1, 'Credit Card', '2025-05-01', 51.98, 'Completed'),
    (2, 'PayPal', '2025-05-02', 45.50, 'Pending');

-- Sample CARTITEMS
INSERT INTO CartItem (customerID, deviceId, quantity, dateAdded)
VALUES
    (1, 1, 1, '2025-04-30'),
    (2, 2, 2, '2025-04-29');
