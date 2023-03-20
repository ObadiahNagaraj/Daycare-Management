package operations;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import connections.DbConnection;
import models.ManagerLogics;

/**
 * Servlet implementation class ViewMyDetails
 */
@WebServlet("/user/ViewMyDetails")
public class ViewMyDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewMyDetails() {
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
//		JSONArray  arr=new JSONArray();
//		JSONObject obj=new JSONObject();
//		try {
//			PreparedStatement psmt1=DbConnection.dbConnection.prepareStatement("select * from Mother where regNo like ?");
//			psmt1.setString(1, (String)request.getAttribute("userId"));
//			ResultSet rs1=psmt1.executeQuery();
//			if(rs1.next()) {
//				obj.put("registerNo", rs1.getString(1));
//				obj.put("name", rs1.getString(2));
//				obj.put("number", rs1.getString(3));
//				obj.put("region", rs1.getString(4));
//				arr.add(obj);
//			}
//			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo like ?");
//			psmt.setString(1, (String)request.getAttribute("userId"));
//			ResultSet rs=psmt.executeQuery();
//			while(rs.next()) {
//				obj=new JSONObject();
//				obj.put("childName", rs.getString(2));
//				obj.put("childAge", rs.getString(4));
//				obj.put("childClass", rs.getString(5));
//				obj.put("childSchool", rs.getString(3));
//				arr.add(obj);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		response.getWriter().append(arr.toJSONString());
		
		ManagerLogics mg=new ManagerLogics();
		JSONArray arr=mg.viewMyDetails((String)request.getAttribute("userId"));
		response.getWriter().append(arr.toJSONString());
		
	}

}
