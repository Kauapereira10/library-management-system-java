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
import exception.BusinessException;
import model.Book;


@WebServlet("/books/details")
public class BookDetailsServlet extends HttpServlet {
	
	private BookDAO bookDao = null;
       
    public BookDetailsServlet() {
    	bookDao = new BookDAO();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Rota acessada: " + request.getServletPath());
		
		String action = request.getServletPath();
		
		switch (action) {
		case "/books/details": 
			detailsBook(request, response);
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
		}
	}
	
	private void detailsBook (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			 String idParam = request.getParameter("id");
			
			if(idParam == null) {
				throw new BusinessException("ID nãp informado.");
			}
			
			int id = Integer.parseInt(idParam);
			
			Book book = bookDao.findById(id);
			
			if(book == null) {
				throw new BusinessException("Livro não Encontrado.");
			}
			
			request.setAttribute("books", book);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/books/book-details.jsp");
			
			dispatcher.forward(request, response);
			
		} catch (NumberFormatException e) { 
			
			request.setAttribute("mensagemErro", "ID inválido");
			request.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(request, response);
			
		} catch (BusinessException e) {
			request.setAttribute("mensagemErro", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(request, response);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			request.setAttribute("mensagemErro", "Erro inesperado");
			request.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(request, response);
			
		}
	}

}
