package controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;


import dao.UserDAO;
import model.User;
import util.PasswordUtil;

@WebServlet(urlPatterns = {"/users/register", "/users/login", "/users/profile"})
public class UserServlet extends HttpServlet{	

	private UserDAO userDao;
	
	@Override
	public void init() {
		userDao = new UserDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Rota acessada: " + request.getServletPath());
		 
		String action = request.getServletPath();

		switch (action) {
		case "/users/register": 
			request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
			break;
		case "/users/login": 
			request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
			break;
		case "/users/profile": 
			HttpSession session = request.getSession();
			if(session != null && session.getAttribute("user") != null) {
				request.getRequestDispatcher("/WEB-INF/views/auth/profile.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "Você precisa estar logado!");
				request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
			}
			
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		boolean success = false;
		
		switch (action) {
		case "/users/register": 
			success = registerUser(request, response);
			return;
		case "/users/login":
			success = loginUser(request, response);
			return;
		case "/users/profile":
			//success = profileUser(request, response);
			return;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
	}
	
	protected boolean registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User newUser = new User();
			String fullName = request.getParameter("name");
			String nickName = request.getParameter("nick name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			String hash = PasswordUtil.hash(password);
			
			newUser = new User(fullName, nickName, email, hash);
			
			//userDao.save();
			
			response.sendRedirect(request.getContextPath() + "/views/auth/login.jsp");
			
		} catch (Exception e) {
			request.setAttribute("erro", e.getMessage());
			request.getRequestDispatcher("/views/auth/resgister.jsp").forward(request, response);
		}
		return false;
	}
	
	private boolean loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
			String email = request.getParameter("email");
			String passwordHash = request.getParameter("passwordHash");
			
			User user = userDao.login(email, passwordHash);
			
			if(user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() +  "/views/books/list.jsp");
			}else {
				request.setAttribute("erro", "");
				request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
			}
			
		
	}
	
	private boolean profileUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception{
		
		
		
		return false;	
			
	}
	
}
