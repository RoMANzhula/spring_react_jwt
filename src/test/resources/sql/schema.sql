CREATE TABLE IF NOT EXISTS `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL,
    `firstName` VARCHAR(255) NOT NULL,
    `lastName` VARCHAR(255) NOT NULL,
    `birthDate` DATE NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phoneNumber` VARCHAR(20) NOT NULL
);
