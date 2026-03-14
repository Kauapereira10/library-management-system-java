package model;

import java.sql.Date;
import java.time.LocalDate;

public class Loan {
	private int id;
	private Book book;
	private User user;
	private Date loanDate;
	private Date returnDate;
	private boolean active;
	
	public Loan() {
	}
	
	public Loan(Book book, User user, Date loanDate, Date returnDate, boolean active) {
		this.book = book;
		this.user = user;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.active = active;
	}

	public Loan(int id, Book book, User user, Date loanDate, Date returnDate, boolean active) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.active = active;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date date) {
		this.loanDate = date;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
