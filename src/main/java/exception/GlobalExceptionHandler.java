package exception;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class GlobalExceptionHandler implements javax.servlet.Filter{
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			
			Throwable rootCause = (e instanceof ServletException && e.getCause()!= null) ? e.getCause() : e;
			handleException(request, response, rootCause);
		}
		
	}
	
	public void handleException(ServletRequest request, ServletResponse response, Throwable e) throws ServletException, IOException {
		
		if (e instanceof UnauthorizedException) {
	        HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse res = (HttpServletResponse) response;
	        req.getSession().setAttribute("loginMessage", e.getMessage());
	        res.sendRedirect(req.getContextPath() + "/users/login");
	        return;
	    }
		
		String errorMessage;
		
		if(e instanceof BusinessException || e instanceof NotFoundException) {
			errorMessage = e.getMessage();
		}else {
			e.printStackTrace();
			errorMessage = "Ocorreu um erro interno no sistema.";
		}
		
		request.setAttribute("errorMessage", errorMessage);
		
		if(!response.isCommitted()) {
			request.getRequestDispatcher("/WEB-INF/views/errors/error.jsp").forward(request, response);
		}
	}
	
	@Override
	public void destroy() {
		
	}
	
}
