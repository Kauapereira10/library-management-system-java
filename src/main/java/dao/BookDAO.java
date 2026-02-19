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
	
}
