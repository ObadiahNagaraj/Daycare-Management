package operations;

import java.io.BufferedReader;
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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mysql.cj.xdevapi.JsonParser;

import connections.DbConnection;
import models.Child;
import models.Daycare;

/**
 * Servlet implementation class AddDaycare
 */
@WebServlet("/admin/AddDaycare")
public class AddDaycare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDaycare() {
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
		 JSONObject jsonObject = new JSONObject();
		 JSONParser parser=new JSONParser();
		 try {
			jsonObject=(JSONObject) parser.parse(values);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		Daycare daycare=new Daycare();
		daycare.setName((String)jsonObject.get("Name"));
		daycare.setPhoneNo((String)jsonObject.get("Number"));
		daycare.setFee(Integer.parseInt((String)jsonObject.get("Fee")));
		daycare.setRegion((String)jsonObject.get("Region"));
		Child child=new Child();
		child.setName((String)jsonObject.get("ChildName"));
		child.setClass((String)jsonObject.get("ChildClass"));
		child.setSchool((String)jsonObject.get("ChildSchool"));
		int regNo=0;
		try {
			Statement stmt=DbConnection.dbConnection.createStatement();
			ResultSet rs=stmt.executeQuery("select count(*) from Daycare");
			if(rs.next()) {
				regNo=rs.getInt(1)+1111;
			}
			PreparedStatement pstmt=DbConnection.dbConnection.prepareStatement("insert into Daycare values(?,?,?,?,?,'NOT_ENROLLED')");
			pstmt.setString(1, daycare.getName()+regNo);
			pstmt.setString(2, daycare.getName());
			pstmt.setString(3, daycare.getPhoneNo());
			pstmt.setString(4, daycare.getRegion());
			pstmt.setString(5, daycare.getFee()+"");
			int first=pstmt.executeUpdate();
			pstmt=DbConnection.dbConnection.prepareStatement("insert into Child(name,school,dob,class,mother_regNo) values(?,?,?,?,?)");
			pstmt.setString(1,child.getName());
			pstmt.setString(2,child.getSchool());
			pstmt.setString(3,(String)jsonObject.get("ChildAge"));
			pstmt.setString(4,child.getClass1());
			pstmt.setString(5,daycare.getName()+regNo);
			int second=pstmt.executeUpdate();
			if(first==1 && second==1) {
				response.getWriter().append("successfully added");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
