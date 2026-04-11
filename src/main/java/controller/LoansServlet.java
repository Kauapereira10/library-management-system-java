package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LoansDAO;
import exception.BusinessException;
import exception.UnauthorizedException;
import model.Loan;
import model.User;

@WebServlet("/loans/*")
public class LoansServlet extends HttpServlet{

	private LoansDAO dao = null;
	
	public LoansServlet() {
		dao = new LoansDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

			session(request, response);
			
			String action = getPath(request);
			
			if(action == null || action.equals("/")) {
				listLoans(request, response);
			} else {
				throw new BusinessException("Ação Inválida.");
			}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
			session(request, response);
			
			String action = getPath(request);
			
			if(action.equals("/borrow")) {
				borrowBook(request, response);
			} else if (action.equals("/return")) {
				returnBook(request, response);
			} else {
                throw new BusinessException("Ação inválida.");
            }
		
	}
	
	private void listLoans(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		List<Loan> loans = dao.findByUser(user.getId());
		request.setAttribute("loans", loans);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/loans/my-loans.jsp");
		dispatcher.forward(request, response);
	}
	
	private void borrowBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		dao.creatLoan(bookId, user.getId());
		
		response.sendRedirect(request.getContextPath() + "/loans");
	}
	
	private void returnBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		int loanId = Integer.parseInt(request.getParameter("id"));
		dao.returnBook(loanId);
		
		response.sendRedirect(request.getContextPath() + "/loans");
	}	
	
	private String getPath(HttpServletRequest request) {
		String path = request.getPathInfo();
		return (path == null) ? "/" : path;
				
	}
	
	private User session(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null) {
			throw new UnauthorizedException("Você precisa estar logado.");
		}
		
		User user = (User) session.getAttribute("user");
		return user;
	}
	
	
}