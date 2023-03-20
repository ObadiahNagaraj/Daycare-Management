package operations;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connections.DbConnection;
import models.Daycare;
import models.Mother;
import models.Status;


/**
 * Servlet implementation class Enroll
 */
@WebServlet("/user/Enroll")
public class Enroll extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Enroll() {
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
		String daycareId=request.getParameter("daycareId");
		Daycare daycare = null;
		Mother mom=null;
		try {
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement("select * from Daycare where dayCareId=?");
			pstmt.setString(1, daycareId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				daycare=new Daycare();
				daycare.setId(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		String userId=(String)request.getAttribute("userId");
		try {
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement("select * from Mother where regNo=?");
			pstmt.setString(1, userId);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				mom=new Mother();
				mom.setRegNo(rs.getString(1));
				mom.setName(rs.getString(2));
				mom.setPhoneNo(rs.getString(3));
				mom.setRegion(rs.getString(4));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement("update Mother set dayCareId=? where regNo=?");
			pstmt.setString(1, daycare.getId());
			pstmt.setString(2, mom.getRegNo());
			pstmt.executeUpdate();
			pstmt=DbConnection.dbConnection.prepareStatement("update Daycare set status='ENROLLED' where dayCareId=?");
			pstmt.setString(1, daycare.getId());
			pstmt.executeUpdate();
			pstmt=DbConnection.dbConnection.prepareStatement("insert into Relations values(?,?,?)");
			pstmt.setString(1, daycare.getId());
			pstmt.setString(2, mom.getRegNo());
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
			LocalDateTime currentTimeDate = LocalDateTime.now();
			pstmt.setString(3, dtf.format(currentTimeDate));
			pstmt.executeUpdate();
			pstmt=DbConnection.dbConnection.prepareStatement("insert into Notification(mother_regNo,daycare_id,date,region) values(?,?,?,?)");
			pstmt.setString(1, mom.getRegNo());
			pstmt.setString(2, daycare.getId());
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			currentTimeDate = LocalDateTime.now();
			pstmt.setString(3, dtf.format(currentTimeDate));
			pstmt.setString(4, mom.getRegion());
			response.getWriter().append("enrolled successfully");
			pstmt.execute();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			response.getWriter().append("error found");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		try {
//			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement("update Mother set dayCareId=? where regNo=?");
//			pstmt.setString(1, daycareId);
//			pstmt.setString(2, userId);
//			pstmt.executeUpdate();
//			pstmt=DbConnection.dbConnection.prepareStatement("update Daycare set status='enrolled' where dayCareId=?");
//			pstmt.setString(1, daycareId);
//			pstmt.executeUpdate();
//			pstmt=DbConnection.dbConnection.prepareStatement("insert into Relations values(?,?)");
//			pstmt.setString(1, daycareId);
//			pstmt.setString(2, userId);
//			pstmt.executeUpdate();
//			response.getWriter().append("enrolled successfully");
//		}catch(Exception ex) {
//			System.out.println(ex.getMessage());
//			response.getWriter().append("error found");
//		}
	}

}
