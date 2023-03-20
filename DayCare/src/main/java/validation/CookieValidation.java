package validation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import connections.DbConnection;

/**
 * Servlet implementation class CookieValidation
 */
@WebServlet("/CookieValidation")
public class CookieValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieValidation() {
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
		String sessionId=request.getParameter("sessionId");
		String id="";
		JSONObject obj=new JSONObject();
		try {
			System.out.println(sessionId);
			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select regNo from Cookie where sessionId like ?");
			psmt.setString(1, sessionId);
			ResultSet rs=psmt.executeQuery();
			if(rs.next()) {
				id=rs.getString(1);
			}
			psmt=DbConnection.dbConnection.prepareStatement("select * from Admin where name like ?");
			psmt.setString(1, id);
			rs=psmt.executeQuery();
			if(rs.next()) {
				String name=rs.getString(1);
				obj.put("name", name);
				obj.put("role","admin");
				obj.put("Message", "Cookie exist");
				response.getWriter().append(obj.toString());
			}
			psmt=DbConnection.dbConnection.prepareStatement("select * from Mother where regNo like ?");
			psmt.setString(1, id);
			rs=psmt.executeQuery();
			if(rs.next()) {
				String name=rs.getString(2);
//				String password=rs.getString(3);
				obj.put("name", name);
//				obj.put("password", password);
				obj.put("role","mother");
				obj.put("Message", "Cookie exist");
				response.getWriter().append(obj.toString());
			}
			if(obj.isEmpty()) {
				obj.put("Message", "Cookie doesn't exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
