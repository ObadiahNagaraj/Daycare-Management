package filters;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;



import connections.DbConnection;


/**
 * Servlet Filter implementation class Authentication
 */
@WebFilter(urlPatterns = {"/user/*","/admin/*"})
public class Authentication extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public Authentication() {
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
		System.out.println("first filter");
		String sessionId="";
		String userId="";
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		Cookie[] cookieArray=httpRequest.getCookies();
		for(int i=0;i<cookieArray.length;i++) {
			if(cookieArray[i].getName().equals("SessionId")) {
				sessionId = cookieArray[i].getValue();
				break;
			}
		}
		try {
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Cookie where sessionId=?");
			psmt.setString(1, sessionId);
			ResultSet rs=psmt.executeQuery();
			if(rs.next()) {
				userId=rs.getString(2);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(userId);
		if(userId!="") {
			request.setAttribute("userId", userId);
			chain.doFilter(request, response);
		}
		else {
			response.getWriter().append("user id not exists");
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
