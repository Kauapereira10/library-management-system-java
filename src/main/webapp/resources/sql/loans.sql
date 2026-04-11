CREATE TABLE library_db.loans (
	id INT UNSIGNED AUTO_INCREMENT NOT NULL,
	book_id INT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    loan_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    return_date DATE,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_loans PRIMARY KEY (id),
    CONSTRAINT fk_loans_books FOREIGN KEY (book_id) REFERENCES library_db.books (id), 
    CONSTRAINT fk_loans_users FOREIGN KEY (user_id) REFERENCES library_db.users	(id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;