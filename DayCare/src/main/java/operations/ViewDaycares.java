package operations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import connections.DbConnection;
import models.ManagerLogics;

/**
 * Servlet implementation class ViewDaycares
 */
@WebServlet(urlPatterns = {"/user/ViewDaycares", "/admin/ViewDaycares"})
public class ViewDaycares extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDaycares() {
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

		ManagerLogics mg=new ManagerLogics();
		if(request.getAttribute("role").equals("mother")) {
			JSONArray arr=mg.viewDaycares((String)request.getAttribute("userId"));
			response.getWriter().append(arr.toJSONString());
		}
		else if(request.getAttribute("role").equals("admin")) {
			JSONArray arr=mg.viewDaycares((String)request.getAttribute("role"),request.getParameter("region"));
			response.getWriter().append(arr.toJSONString());
		}
		
	}

}
