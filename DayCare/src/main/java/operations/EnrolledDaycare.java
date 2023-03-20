package operations;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

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
 * Servlet implementation class EnrolledDaycare
 */
@WebServlet("/user/EnrolledDaycare")
public class EnrolledDaycare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrolledDaycare() {
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
//		String userId=(String) request.getAttribute("userId");
//		String daycareId;
//		JSONArray arr=new JSONArray();
//		try {
//			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select dayCareId from Mother where regNo like ?");
//			psmt.setString(1, userId);
//			ResultSet rs=psmt.executeQuery();
//			
//			if(rs.next()) {
//				daycareId=rs.getString(1);
//				PreparedStatement pstmt1=DbConnection.dbConnection.prepareStatement("select * from Daycare where dayCareId=?");
//				pstmt1.setString(1, daycareId);
//				ResultSet rs1=pstmt1.executeQuery();
//				if(rs1.next()) {
//					JSONObject obj=new JSONObject();
//					obj.put("name", rs1.getString(2));
//					obj.put("number", rs1.getString(3));
//					obj.put("region", rs1.getString(4));
//					obj.put("fee", rs1.getString(5));
//					arr.add(obj);
//					pstmt1=DbConnection.dbConnection.prepareStatement("select * from Child where mother_regNo=?");
//					pstmt1.setString(1, daycareId);
//					rs1=pstmt1.executeQuery();
//					while(rs1.next()) {
//						obj=new JSONObject();
//						obj.put("childName", rs1.getString(2));
//						obj.put("childAge", rs1.getString(4));
//						obj.put("childClass", rs1.getString(5));
//						obj.put("childSchool", rs1.getString(3));
//						arr.add(obj);
//					}
//				}	
//			}
//			
//			response.getWriter().append(arr.toString());
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		ManagerLogics mg=new ManagerLogics();
		JSONArray arr=mg.myDaycare((String) request.getAttribute("userId"));
		response.getWriter().append(arr.toString());
	}

}
