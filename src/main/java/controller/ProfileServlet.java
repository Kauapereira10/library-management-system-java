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
import exception.NotFoundException;
import model.User;
import util.PasswordUtil;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {
	private UserDAO userDao;
	
	@Override
	public void init() {
	    userDao = new UserDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/users/login");
			return;
		}
		
		User sessionUser = (User) session.getAttribute("user");
		User userFromDb = userDao.findById(sessionUser.getId());
		
		request.setAttribute("user", userFromDb);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/edit-profile.jsp");
		dispatcher.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/users/login");
			return;
		}
		
		String action = getPath(request);
		
		switch (action) {
		case "/edit": 
			editProfile(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		
	}
	
	
	
	private void editProfile(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
		HttpSession session = request.getSession(false);
		User sessionUser = (User) session.getAttribute("user");
		int id = sessionUser.getId();
		String fullname = request.getParameter("full_name");
		String nickName = request.getParameter("nick_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		validateUser(fullname, nickName, email, password);
		
		String hash = PasswordUtil.hash(password);

		User profile = new User(id, fullname, nickName, email, hash);
		
		if(!userDao.update(profile)) {
			throw new NotFoundException("Usuário não encontrado para atualização.");
		}
		
		response.sendRedirect(request.getContextPath() + "/users/profile");
		
	}
	
	private String getPath(HttpServletRequest request) {
		String path = request.getPathInfo();
		return (path == null) ? "/" : path;
				
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
