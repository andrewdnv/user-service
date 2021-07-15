CREATE TABLE users
(
    id IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    patronymic_name VARCHAR(255),
    date_of_birth DATE,
    date_of_registration DATE NOT NULL,
    city VARCHAR(255),
    mobile_phone VARCHAR(15),
    email VARCHAR(127),
    status VARCHAR(31) NOT NULL
);