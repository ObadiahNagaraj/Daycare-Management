package operations;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import connections.DbConnection;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
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
		String values="";
		String reader="";
		BufferedReader bf=request.getReader();
		while((reader=bf.readLine())!=null) {
			values+=reader;
		}
		JSONObject jsonObject=new JSONObject();
		JSONParser parser=new JSONParser();
		try {
			jsonObject = (JSONObject) parser.parse(values);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(jsonObject);
		int regNo=0;
		try {
			Statement stmt=DbConnection.dbConnection.createStatement();
			ResultSet rs=stmt.executeQuery("select count(*) from Mother");
			if(rs.next()) {
				regNo=rs.getInt(1)+1111;
			}
			PreparedStatement pstmt=DbConnection.dbConnection.prepareStatement("insert into Mother values(?,?,?,?,null,?)");
			pstmt.setString(1, (String)jsonObject.get("Name")+regNo);
			pstmt.setString(2, (String)jsonObject.get("Name"));
			pstmt.setString(3, (String)jsonObject.get("Number"));
			pstmt.setString(4, (String)jsonObject.get("Region"));
			pstmt.setString(5, (String)jsonObject.get("Password"));
			int first=pstmt.executeUpdate();
			pstmt=DbConnection.dbConnection.prepareStatement("insert into Child(name,school,dob,class,mother_regNo) values(?,?,?,?,?)");
			pstmt.setString(1,(String)jsonObject.get("childName"));
			pstmt.setString(2,(String)jsonObject.get("childSchool"));
			pstmt.setString(3,(String)jsonObject.get("childAge"));
			pstmt.setString(4,(String)jsonObject.get("childClass"));
			pstmt.setString(5,(String)jsonObject.get("Name")+regNo);
			int second=pstmt.executeUpdate();
			if(first==1 && second==1) {
				JSONObject obj=new JSONObject();
				obj.put("id", (String)jsonObject.get("Name")+regNo);
				obj.put("result", "signed up successfully");
				response.getWriter().append(obj.toString());
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
