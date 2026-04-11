package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import exception.BusinessException;
import model.User;
import util.PasswordUtil;

/*@WebServlet(urlPatterns = {"/users/register", "/users/login", "/users/profile", "/users/logout"})*/
@WebServlet("/users/*")
public class UserServlet extends HttpServlet{	

	private UserDAO userDao;
	
	@Override
	public void init() {
		userDao = new UserDAO();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Rota acessada: " + request.getServletPath());
		 
		String action = getPath(request);

		switch (action) {
		case "/register": 
			forward(request, response, "/WEB-INF/views/auth/register.jsp");
			break;
		case "/login": 
			forward(request, response, "/WEB-INF/views/auth/login.jsp");
			break;
		case "/profile": 
			HttpSession session = request.getSession(false);
			if(session != null && session.getAttribute("user") != null) {
				forward(request, response, "/WEB-INF/views/users/profile.jsp");
			} else {
				request.setAttribute("error", "Você precisa estar logado!");
				forward(request, response, "/WEB-INF/views/auth/login.jsp");
			}
			
			break;
		case "/logout":
			logoutUser(request, response);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getPath(request);
		
		switch (action) {
		case "/register": 
			registerUser(request, response);
			return;
		case "/login":
			loginUser(request, response);
			return;
		case "/profile":
			showProfile(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
	}
	
	protected boolean registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User newUser = new User();
			String fullName = request.getParameter("full_name");
			String nickName = request.getParameter("nick_name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			validateUser(fullName, nickName, email, password);
			
			String hash = PasswordUtil.hash(password);
			newUser = new User(fullName, nickName, email, hash);
			userDao.register(newUser);
			
			response.sendRedirect(request.getContextPath() + "/users/login");;
			
		} catch (Exception e) {
			request.setAttribute("erro", e.getMessage());
			forward(request, response, "/WEB-INF/views/auth/register.jsp");
		}
		return false;
	}
	
	private boolean loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			User user = userDao.login(email, password);
			
			if(user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/users/profile");
			}else {
				request.setAttribute("error", "Email ou senha inválidos.");
				forward(request, response, "/WEB-INF/views/auth/login.jsp");
			}
			
			
			return false;
		
	}
	
	private void showProfile(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    HttpSession session = request.getSession(false);

	    if (session == null || session.getAttribute("user") == null) {
	        response.sendRedirect(request.getContextPath() + "/users/login");
	        return;
	    }

	    User sessionUser = (User) session.getAttribute("user");

	    User userFromDb = userDao.findById(sessionUser.getId());
	    
	    request.setAttribute("user", userFromDb);
	    forward(request, response, "/WEB-INF/views/users/profile.jsp");
	}
	
	private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath() + "/users/login");
	}

	// HELPERS
	
	private String getPath(HttpServletRequest request) {
		String path = request.getPathInfo();
		return (path == null) ? "/" : path;
				
	}
	
	private void forward (HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	private void validateUser(String fullName, String nickName, String email, String password) {
		if(fullName == null || fullName.isBlank()) {
			throw new BusinessException("Nome completo é obrigatório.");
		}
		
		if(nickName == null || nickName.isBlank()) {
			throw new BusinessException("Nome do usuario é obrigatório.");
		}
		
		if(email == null || email.isBlank()) {
			throw new BusinessException("Email é obrigatório.");
		}
		
		if(password == null || password.isBlank()) {
			throw new BusinessException("Senha é obrigatório.");
		}
	}
	
}
