package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookDAO;
import model.Book;




@WebServlet("/books")
public class BookServlet extends HttpServlet {

	private BookDAO bookDao = null;

	public BookServlet() {
		bookDao = new BookDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Rota acessada: " + request.getServletPath());
		
		String action = request.getServletPath();
		
		switch (action) {
		case "/books": 
			listBooks(request, response);
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Book> books = bookDao.findAll();
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/books/list.jsp");
		dispatcher.forward(request, response);
	}
	
}
