package filters;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import connections.DbConnection;

/**
 * Servlet Filter implementation class Authorization
 */
@WebFilter("/user/*")
public class AuthorizationForMother extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AuthorizationForMother() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		// pass the request along the filter chain
		System.out.println("second filter");
		String userId=(String) request.getAttribute("userId");
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Mother where regNo=?");
			psmt.setString(1, userId);
			ResultSet rs=psmt.executeQuery();
			if(rs.next()) {
				request.setAttribute("role", "mother");
				chain.doFilter(request, response);
			}
			else {
				response.getWriter().append("Your not allowed to do this action");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
