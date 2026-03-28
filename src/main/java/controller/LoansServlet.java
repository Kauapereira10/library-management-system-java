package controller;

import java.io.IOException;
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
import exception.handleError;
import model.Loan;
import model.User;

@WebServlet("/loans/*")
public class LoansServlet extends HttpServlet{

	private LoansDAO dao = null;
	
	public LoansServlet() {
		dao = new LoansDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		try {
			HttpSession session = request.getSession(false);
			
			if(session == null || session.getAttribute("user") == null) {
				response.sendRedirect(request.getContextPath() + "/users/login");
				return;
			}
			
			User user = (User) session.getAttribute("user");
			
			String action = request.getPathInfo();
			
			if(action == null || action.equals("/")) {
				listLoans(request, response);
			} else if (action.equals("/return")) {
				returnBook(request, response);
			} else {
				throw new BusinessException("Ação Inválida.");
			}
			
		} catch (BusinessException e) {
           
			handleError.handle(request, response, e.getMessage());
			
        } catch (Exception e) {
           e.printStackTrace();
           handleError.handle(request, response, "Erro inesperado.");
        }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
		try {
			
			HttpSession session = request.getSession(false);
			
			if(session == null || session.getAttribute("user") == null) {
				response.sendRedirect(request.getContextPath() + "/users/login");
				return;
			}
			
			User user = (User) session.getAttribute("user");
			
			String action = request.getPathInfo();
			
			if(action.equals("/borrow")) {
				borrowBook(request, response);
			} else {
                throw new BusinessException("Ação inválida.");
            }
			
			
		} catch (BusinessException e) {
			handleError.handle(request, response, e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            handleError.handle(request, response, e.getMessage());
        }
		
//		System.out.println("Rota acessada borrowBook: " + request.getPathInfo());
//
//		String action = request.getPathInfo();
//		
//		
//		if(action.equals("/borrow")) {
//			borrowBook(request, response);
//		}
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
}