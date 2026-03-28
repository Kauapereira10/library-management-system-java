package exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface handleError {

	public static void handle(HttpServletRequest request, HttpServletResponse response, String message)
	        throws ServletException, IOException {

	    request.setAttribute("mensagemErro", message);
	    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
	}
}
