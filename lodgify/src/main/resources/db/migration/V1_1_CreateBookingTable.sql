CREATE TABLE booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    traveler_id INT,
    host_id INT,
    residence_id INT,
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (traveler_id) REFERENCES user(id),
    FOREIGN KEY (host_id) REFERENCES user(id),
    FOREIGN KEY (residence_id) REFERENCES residence(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
