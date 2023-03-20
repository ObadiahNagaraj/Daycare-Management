package validation;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connections.DbConnection;

/**
 * Servlet implementation class ConfiguringDb
 */
@WebServlet(urlPatterns = "/ConfiguringDb",
		initParams = {@WebInitParam(name = "dbUser", value = "obadiah-zstk304"),
				@WebInitParam(name="password",value="Wwhzfmneol+xpv1"),
				@WebInitParam(name="dbName",value="daycaremanagement")},
		loadOnStartup= 0
)
public class ConfiguringDb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfiguringDb() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	String dbName="jdbc:mysql://localhost:3306/"+getServletConfig().getInitParameter("dbName");
    	String username=getServletConfig().getInitParameter("dbUser");
    	String password=getServletConfig().getInitParameter("password");
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			DbConnection.dbConnection = DriverManager.getConnection(dbName, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}    	
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
		doGet(request, response);
	}

}
