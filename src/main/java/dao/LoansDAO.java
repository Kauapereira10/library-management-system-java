package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Book;
import model.Loan;
import util.ConnectionFactory;

public class LoansDAO {
	
	public void creatLoan(int bookId, int userId) {
		String sql = "INSERT INTO loans (book_id, user_id, loan_date, active) VALUES (?,?,?,?);";
		
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setInt(1, bookId);
			stmt.setInt(2, userId);
			stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stmt.setBoolean(4, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Loan> findByUser(int userId) {
		List<Loan> loans = new ArrayList<>();
		
		String sql = """
		        SELECT l.*, b.title, b.author, b.isbn
		        FROM loans l
		        JOIN books b ON b.id = l.book_id
		        WHERE l.user_id = ? AND l.active = true
		               """;
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Book book = new Book();
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setIsbn(rs.getString("isbn"));
				
				Loan loan = new Loan();
				loan.setId(rs.getInt("id"));
				loan.setBook(book);
				loan.setLoanDate(rs.getDate("loan_date"));
				loan.setActive(rs.getBoolean("active"));
				
				loans.add(loan);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loans;
	}
	
	public void returnBook(int loanId) {
		String sql = "UPDATE loans SET active = false, return_date = ? WHERE id = ?";
		
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stmt.setInt(2, loanId);
			
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
