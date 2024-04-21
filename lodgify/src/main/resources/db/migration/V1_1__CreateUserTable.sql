CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
<<<<<<< Updated upstream
    phone_number INT(9), -- Assuming international numbers might be included; adjust length as needed
=======
    phone_number VARCHAR(9) NOT NULL UNIQUE, -- Assuming international numbers might be included; adjust length as needed
    user_type VARCHAR(4),
    id_card VARCHAR(255),
    bank_account INT,
    social_SN INT,
    address VARCHAT(255),
>>>>>>> Stashed changes
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


