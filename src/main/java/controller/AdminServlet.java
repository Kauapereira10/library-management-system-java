package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import dao.BookDAO;
import model.Book;
import model.User;


@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
	
	private BookDAO bookDao;
	
	@Override
	public void init() {
		bookDao = new BookDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// VERIFICA SE É ADMIN
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/users/login");
			return;
		}
		
		User user = (User) session.getAttribute("user");
	
		if(!"Admin".equals(user.getRole())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		String path = request.getPathInfo();
		
		if(path == null || path.equals("/")) {
			request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
		} else if(path.equals("/books")) {
			request.getRequestDispatcher("/WEB-INF/views/admin/book-list.jsp").forward(request, response);
		} else if(path.equals("/books/new")) {
			request.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp").forward(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getPathInfo();
		
		switch (action) {
		case "/admin/books/new":
			newBook(request, response);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	
	private void newBook(HttpServletRequest request, HttpServletResponse response)  throws IOException{
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		
		Book book = new Book(title, author, isbn, true, true);
		bookDao.create(book);
		
		response.sendRedirect(request.getContextPath() + "/admin/books");
	}

}
