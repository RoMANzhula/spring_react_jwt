CREATE TABLE IF NOT EXISTS `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `birth_date` DATE NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL
);
