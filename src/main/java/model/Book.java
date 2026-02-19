package model;

public class Book {
	private int id;
	private String title;
	private String author;
	private String isbn;
	private boolean available;
	private boolean active;
	
	public Book() {
		
	}
	
	public Book(String title, String author, String isbn, boolean available, boolean active) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.available = available;
		this.active = active;
	}

	public Book(int id, String title, String author, String isbn, boolean available, boolean active) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.available = available;
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
