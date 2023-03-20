package validation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import connections.DbConnection;

/**
 * Servlet implementation class LoginValidation
 */
@WebServlet(urlPatterns="/LoginValidation")
public class LoginValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginValidation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		try {
			PreparedStatement pstmt=DbConnection.dbConnection.prepareStatement("select * from Admin where name=?");
			pstmt.setString(1, username);
			ResultSet rs1=pstmt.executeQuery();
			if(rs1.next()) {
				if(password.equals(rs1.getString(2))) {
					UUID uuid=UUID.randomUUID();
					Cookie cookie=new Cookie("SessionId",uuid+"");
					pstmt=DbConnection.dbConnection.prepareStatement("insert into Cookie values(?,?)");
					pstmt.setString(1, uuid+"");
					pstmt.setString(2, username);
					pstmt.execute();
					response.addCookie(cookie);
					response.getWriter().append("admin login");
				}
				else {
					response.getWriter().append("Invalid password");
				}
			}
			else {
				PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Mother where regNo=?");
				psmt.setString(1, username);
				ResultSet rs=psmt.executeQuery();
				if(rs.next()) {
					if(password.equals(rs.getString(6))) {
						UUID uuid=UUID.randomUUID();
						Cookie cookie=new Cookie("SessionId",uuid+"");
						psmt=DbConnection.dbConnection.prepareStatement("insert into Cookie values(?,?)");
						psmt.setString(1, uuid+"");
						psmt.setString(2, username);
						psmt.execute();
						response.addCookie(cookie);
						response.getWriter().append("mother login");
					}
				}
				else {
					response.getWriter().append("Invalid password");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
