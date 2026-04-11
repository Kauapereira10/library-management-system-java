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
import dao.LoansDAO;
import dao.UserDAO;
import exception.BusinessException;
import exception.NotFoundException;
import model.Book;
import model.User;


@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
	
	private BookDAO bookDao;
	private UserDAO userDao;
	private LoansDAO loansDao;
	
	@Override
	public void init() {
		bookDao = new BookDAO();
		userDao = new UserDAO();
		loansDao = new LoansDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!validateAdmin(request, response)) return;
		
		String path = getPath(request);
		
		switch (path) {
		case "/dashboard": 
			loadDashboard(request, response);
			break;
			
		case "/books":
			listBooks(request, response);
			break;
			
		case "/books/new": 
			forward(request, response, "/WEB-INF/views/admin/book-form.jsp");
			break;
			
		case "/books/edit":
			showEditForm(request, response);
			break;
		default:
			throw new NotFoundException("Rota não encontrada.");
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
		
		if(!validateAdmin(request, response)) return;

	    String path = getPath(request);
	    
	    	switch (path) {

	        case "/books/new":
	            createBook(request, response);
	            break;

	        case "/books/update":
	            updateBook(request, response);
	            break;

	        default:
	            throw new NotFoundException("Ação inválida.");
	    }
	    	

	}
	
	//VALIDAÇÃO
	
	public boolean validateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/users/login");
			return false;
		}
		
		User user = (User) session.getAttribute("user");
		
		if(!"Admin".equals(user.getRole())) {
			throw new BusinessException("Acesso negado.");
		}
		
		return true;
	}
	
	// AÇÕES
	
	private void listBooks(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
		
		List<Book> books = bookDao.findAll();
		request.setAttribute("books", books);
		
		forward(request, response, "/WEB-INF/views/admin/book-list.jsp");
	}
	
	private void createBook(HttpServletRequest request, HttpServletResponse response)  throws IOException, BusinessException{
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		
		validateBook(title, author, isbn);
		
		Book book = new Book(title, author, isbn, true, true);
		bookDao.create(book);
		
		response.sendRedirect(request.getContextPath() + "/admin/books");
	}
	
	private void updateBook(HttpServletRequest request, HttpServletResponse response)  throws IOException, ServletException{
		
		int id = parseId(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		
		validateBook(title, author, isbn);
		
		Book book = new Book(id, title, author, isbn);
		
		if(!bookDao.update(book)) {
			throw new NotFoundException("Livro não encontrado para atualização.");
		}

		response.sendRedirect(request.getContextPath() + "/admin/books");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		int id = parseId(request.getParameter("id"));

	    Book book = bookDao.findById(id);
	    
	    if(book == null) {
	    	throw new NotFoundException("Livro não encontrado.");
	    }

	    request.setAttribute("book", book);

	    forward(request, response, "/WEB-INF/views/admin/book-form.jsp");
	}
	
	private void loadDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("totalBooks", bookDao.countAll());
		request.setAttribute("availableBooks", bookDao.countAvailable());
		request.setAttribute("borrowedBooks", bookDao.countBorrowed());
		request.setAttribute("totalUsers", userDao.countAll());
		request.setAttribute("activeLoans", loansDao.countActive());
		request.setAttribute("recentLoans", loansDao.findRecent(5));
		
		forward(request, response, "/WEB-INF/views/admin/dashboard.jsp");
	}
	
	// HELPERS
	
	private String getPath(HttpServletRequest request) {
		String path = request.getPathInfo(); // retorna "/", "/books", "/books/edit" ...
		return (path == null) ? "/" : path; 
	}
	
	private int parseId(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (Exception e) {
            throw new BusinessException("ID inválido.");
        }
    }
	
	private void validateBook(String title, String author, String isbn) {
		
		if(title == null || title.isBlank()) {
			throw new BusinessException("Título é obrigatório.");
		}
		if(author == null || author.isBlank()) {
			throw new BusinessException("Autor é obrigatório.");
		}
		if(isbn == null || isbn.isBlank()) {
			throw new BusinessException("ISBN é obrigatório.");
		}
	}
	
	
	
	private void forward (HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}
	
	
	
	
	
	

}
