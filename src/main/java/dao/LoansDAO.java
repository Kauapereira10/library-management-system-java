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
	    String sqlLoan = "INSERT INTO loans (book_id, user_id, loan_date, active) VALUES (?,?,?,?);";
	    String sqlBook = "UPDATE books SET available = false WHERE id = ?;";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement stmtLoan = con.prepareStatement(sqlLoan);
	         PreparedStatement stmtBook = con.prepareStatement(sqlBook)) {
	        
	        // Cria o empréstimo
	        stmtLoan.setInt(1, bookId);
	        stmtLoan.setInt(2, userId);
	        stmtLoan.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
	        stmtLoan.setBoolean(4, true);
	        stmtLoan.executeUpdate();
	        
	        // Marca o livro como indisponível
	        stmtBook.setInt(1, bookId);
	        stmtBook.executeUpdate();
	        
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
	    String sqlLoan = "UPDATE loans SET active = false, return_date = ? WHERE id = ?";
	    String sqlBook = "UPDATE books SET available = true WHERE id = (SELECT book_id FROM loans WHERE id = ?)";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement stmtLoan = con.prepareStatement(sqlLoan);
	         PreparedStatement stmtBook = con.prepareStatement(sqlBook)) {
	        
	        // Fecha o empréstimo
	        stmtLoan.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
	        stmtLoan.setInt(2, loanId);
	        stmtLoan.executeUpdate();
	        
	        // Marca o livro como disponível novamente
	        stmtBook.setInt(1, loanId);
	        stmtBook.executeUpdate();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
