package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Book;
import model.Loan;
import model.User;
import util.ConnectionFactory;

public class LoansDAO {
	
	public void creatLoan(int bookId, int userId) {
	    String sqlLoan = "INSERT INTO loans (book_id, user_id, loan_date, active) VALUES (?,?,?,?);";
	    String sqlBook = "UPDATE books SET available = false WHERE id = ?;";
	    
	    try (Connection con = ConnectionFactory.getConnection()) {
	        con.setAutoCommit(false); // inicia transação
	        try (PreparedStatement stmtLoan = con.prepareStatement(sqlLoan);
	             PreparedStatement stmtBook = con.prepareStatement(sqlBook)) {

	            stmtLoan.setInt(1, bookId);
	            stmtLoan.setInt(2, userId);
	            stmtLoan.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
	            stmtLoan.setBoolean(4, true);
	            stmtLoan.executeUpdate();

	            stmtBook.setInt(1, bookId);
	            stmtBook.executeUpdate();

	            con.commit(); // confirma tudo junto
	        } catch (Exception e) {
	            con.rollback(); // desfaz se algo falhar
	            throw new RuntimeException("Erro ao criar empréstimo.", e);
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Erro de conexão.", e);
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
	    
	    try (Connection con = ConnectionFactory.getConnection()) {
	        con.setAutoCommit(false); // inicia transação
	        try (PreparedStatement stmtLoan = con.prepareStatement(sqlLoan);
	             PreparedStatement stmtBook = con.prepareStatement(sqlBook)) {
	        
	        // Fecha o empréstimo
	        stmtLoan.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
	        stmtLoan.setInt(2, loanId);
	        stmtLoan.executeUpdate();
	        
	        // Marca o livro como disponível novamente
	        stmtBook.setInt(1, loanId);
	        stmtBook.executeUpdate();
	        
	        con.commit();
	    } catch (Exception e) {
	    	con.rollback();
	    	throw new RuntimeException("Erro ao criar fazer o emprestimo ou devolver.", e);
	    }
	    	
	    } catch (Exception e) {
	    	throw new RuntimeException("Erro de conexão.", e);
		}
	}
	
	public int countActive() {
		String sql = "SELECT COUNT(*) FROM loans WHERE active = true;";
		try (Connection con = ConnectionFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
						ResultSet rs = stmt.executeQuery();){
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Loan> findRecent(int limit) {
		List<Loan> loans = new ArrayList<>();
		String sql = """
				SELECT l.*, b.title, b.author, u.full_name
				FROM loans AS l
				JOIN books b ON b.id = l.book_id
				JOIN users u ON u.id = l.user_id
				ORDER BY l.loan_date DESC
				LIMIT ?
				""";
		try (Connection con = ConnectionFactory.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);){
			stmt.setInt(1, limit);
				
				try (ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						Book book = new Book();
						book.setTitle(rs.getString("title"));
						book.setAuthor(rs.getString("author"));
						
						User user = new User();
						user.setFullName(rs.getString("full_name"));
						
						Loan loan = new Loan();
						loan.setId(rs.getInt("id"));
						loan.setBook(book);
						loan.setUser(user);
						loan.setLoanDate(rs.getDate("loan_date"));
						loan.setReturnDate(rs.getDate("return_date"));
						loan.setActive(rs.getBoolean("active"));
						
						loans.add(loan);
					}
				} 
		} catch (Exception e) {
	        e.printStackTrace();
		}
		
		return loans;
	}
}
