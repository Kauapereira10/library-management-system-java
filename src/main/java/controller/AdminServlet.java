package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import exception.BusinessException;
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
			listBooks(request, response);
		} else if(path.equals("/books/new")) {
			request.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp").forward(request, response);
		} else if(path.equals("/books/edit")) {
			showEditForm(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {

	    String action = request.getPathInfo();

	    try {
	    
	    	switch (action) {

	        case "/books/new":
	            createBook(request, response);
	            break;

	        case "/books/update":
	            updateBook(request, response);
	            break;

	        default:
	            throw new BusinessException("Ação inválida.");
	    }
	    
	    } catch (BusinessException e) {
			
	    	request.setAttribute("mensagemErro", e.getMessage());
	    	request.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mensagemErro", "Erro inesperado.");
			request.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(request, response);
		}
	}
	
	private void createBook(HttpServletRequest request, HttpServletResponse response)  throws IOException{
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		
		Book book = new Book(title, author, isbn, true, true);
		bookDao.create(book);
		
		response.sendRedirect(request.getContextPath() + "/admin/books");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    int id = Integer.parseInt(request.getParameter("id"));

	    Book book = bookDao.findById(id);

	    request.setAttribute("book", book);

	    RequestDispatcher dispatcher =
	            request.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp");

	    dispatcher.forward(request, response);
	}
	
	private void listBooks(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
		
		List<Book> books = bookDao.findAll();
		
		request.setAttribute("books", books);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/admin/book-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateBook(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
	
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		
		Book book = new Book(id, title, author, isbn);
		
		bookDao.update(book);
		
		response.sendRedirect(request.getContextPath() + "/admin/books");
		
	}

}
