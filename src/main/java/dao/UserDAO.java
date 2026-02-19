package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import util.ConnectionFactory;
import util.PasswordUtil;

public class UserDAO {
	
	public boolean register(User user) {
		String sql = "INSERT INTO users (full_name, nick_name, email, password_hash) VALUES (?,?,?,?);";
		
		try(Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, user.getFullName());
			stmt.setString(2, user.getNickName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPasswordHash());
			
			int linhasAfetadas = stmt.executeUpdate();
			
			if(linhasAfetadas > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return false;
	}
	
	public User login(String email, String password) {
		
		String sql = "SELECT * FROM users WHERE email = ?";

		try(Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, email);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				
				String storedHash = rs.getString("password");
			
				if(PasswordUtil.verify(password, storedHash)) {
					
					User user = new User();
					
					user.setId(rs.getInt("id"));
					user.setFullName(rs.getString("full_name"));
					user.setNickName(rs.getString("nick_name"));
					user.setEmail(rs.getString("email"));
					
					return user;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public User findById(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		
		try(Connection con = ConnectionFactory.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFullName(rs.getString("full_name"));
				user.setNickName(rs.getString("nick_name"));
				user.setEmail(rs.getString("email"));
				
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
