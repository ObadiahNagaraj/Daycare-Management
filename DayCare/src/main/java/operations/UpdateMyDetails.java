package operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import connections.DbConnection;

/**
 * Servlet implementation class UpdateMyDetails
 */
@WebServlet("/user/UpdateMyDetails")
public class UpdateMyDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMyDetails() {
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
		String userId=(String)request.getAttribute("userId");
		String values="";
		String reader="";
		BufferedReader bf=request.getReader();
		while((reader=bf.readLine())!=null) {
			values+=reader;
		}
		 JSONObject jsonObject = new JSONObject();
		 JSONParser parser=new JSONParser();
		 try {
			jsonObject=(JSONObject) parser.parse(values);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		 System.out.println((String)jsonObject.get("Name"));
		 try {
			PreparedStatement pstmt=DbConnection.dbConnection.prepareStatement("update Mother set name=?,num=?,region=? where regNo like ?");
			pstmt.setString(1, (String)jsonObject.get("Name"));
			pstmt.setString(2, (String)jsonObject.get("Number"));
			pstmt.setString(3, (String)jsonObject.get("Region"));
			pstmt.setString(4, userId);
			pstmt.execute();
			pstmt=DbConnection.dbConnection.prepareStatement("update Child set name=?,school=?,dob=?,class=? where mother_regNo like ?");
			pstmt.setString(1, (String)jsonObject.get("ChildName"));
			pstmt.setString(2, (String)jsonObject.get("ChildSchool"));
			pstmt.setString(3, (String)jsonObject.get("ChildAge"));
			pstmt.setString(4, (String)jsonObject.get("ChildClass"));
			pstmt.setString(5, userId);
			pstmt.execute();
			response.getWriter().append("updated successfully");
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
