CREATE TABLE labrary_db.users (
	id INT UNSIGNED AUTO_INCREMENT NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    nick_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(65) NOT NULL,
    type_user ENUM('User','Admin'),
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
