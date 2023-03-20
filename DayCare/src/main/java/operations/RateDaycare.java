package operations;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connections.DbConnection;
import models.Comments;
import models.ManagerLogics;

/**
 * Servlet implementation class RateDaycare
 */
@WebServlet("/user/RateDaycare")
public class RateDaycare extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RateDaycare() {
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
//		try {
//			PreparedStatement psmt=DbConnection.dbConnection.prepareStatement("select dayCareId from Mother where regNo like ?");
//			psmt.setString(1, userId);
//			ResultSet rs=psmt.executeQuery();
//			if(rs.next()) {
//				daycareId=rs.getString(1);
//				PreparedStatement pstmt1=DbConnection.dbConnection.prepareStatement("update Daycare set status='NOT_ENROLLED' where dayCareId=?");
//				pstmt1.setString(1, daycareId);
//				pstmt1.executeUpdate();
//				psmt=DbConnection.dbConnection.prepareStatement("insert into Comments(dayCareId,comment,star,mother_regNo,time) values(?,?,?,?,?)");
//				psmt.setString(1, daycareId);
//				psmt.setString(2, request.getParameter("comment"));
//				psmt.setInt(3, Integer.parseInt(request.getParameter("starCount")));
//				psmt.setString(4, userId);
//				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
//				LocalDateTime currentTimeDate = LocalDateTime.now();
//				psmt.setString(5, dtf.format(currentTimeDate));
//				psmt.execute();
//				PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement("update Mother set dayCareId=? where regNo=?");
//				pstmt.setNull(1, Types.VARCHAR);
//				pstmt.setString(2, userId);
//				pstmt.executeUpdate();
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		Comments comment=new Comments();
		comment.setComment(request.getParameter("comment"));
		comment.setStar(Integer.parseInt(request.getParameter("starCount")));
		ManagerLogics mg=new ManagerLogics();
		mg.rateDayCare((String) request.getAttribute("userId"),comment);
	}

}
