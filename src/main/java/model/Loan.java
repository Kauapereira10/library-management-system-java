package model;

import java.time.LocalDate;

public class Loan {
	private Long id;
	private Book book;
	private User user;
	private LocalDate loanDate;
	private LocalDate returnDate;
	private boolean active;
	
	public Loan() {
	}
	
	public Loan(Book book, User user, LocalDate loanDate, LocalDate returnDate, boolean active) {
		this.book = book;
		this.user = user;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.active = active;
	}

	public Loan(Long id, Book book, User user, LocalDate loanDate, LocalDate returnDate, boolean active) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.active = active;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDate getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
