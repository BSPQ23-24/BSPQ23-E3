CREATE TABLE residence (
    id INT AUTO_INCREMENT PRIMARY KEY,
    residence_address VARCHAR(255) NOT NULL,
    residence_type VARCHAR(255) NOT NULL,
    n_rooms INT,
    price FLOAT,
    image_path VARCHAR(255),
    user_id VARCHAR(255),
    FOREIGN KEY {user_id} REFERENCES user(username),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
