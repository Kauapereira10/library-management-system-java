package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import util.ConnectionFactory;

public class BookDAO {
	
	public List<Book> findAll() {
		List<Book> list = new ArrayList<>();
		String sql =  "SELECT * FROM books;";
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
			while(rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setIsbn(rs.getString("isbn"));
				book.setAvailable(rs.getBoolean("available"));
				book.setActive(rs.getBoolean("active"));
				
				list.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	public Book findById(int id) {
		Book book = null;
		
		String sql = "SELECT * FROM books WHERE id = ?";
		
		try(Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setIsbn(rs.getString("isbn"));
				book.setAvailable(rs.getBoolean("available"));
				book.setActive(rs.getBoolean("active"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	public boolean create(Book book) {
		String sql = "INSERT INTO books (title, author, isbn, available, active) VALUES (?,?,?,?,?);";
		
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getIsbn());
			stmt.setBoolean(4, book.isAvailable());
			stmt.setBoolean(5, book.isActive());
			
			int linhasAfetadas = stmt.executeUpdate();
			
			if(linhasAfetadas > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Book book) {
		String sql = "UPDATE books SET title =?, author =?, isbn =? WHERE id =?;";
		
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getIsbn());
			stmt.setInt(4, book.getId());
			
			return stmt.executeUpdate() > 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void delete(Book book) {
		String sql = "DELETE FROM books WHERE id =?;";
		
		try (Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, book.getId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
