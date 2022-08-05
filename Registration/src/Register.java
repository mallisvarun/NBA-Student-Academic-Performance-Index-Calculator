

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private String dbUrl="jdbc:mysql://localhost:3306/userdb";
	private String dbUname="root";
	private String dbPassword="admin";
	private String dbDriver="com.mysql.cj.jdbc.Driver";
	
	public void loadDriver(String dbDriver) {
		try {
		Class.forName(dbDriver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		Connection con=null;
		try {
			con=DriverManager.getConnection(dbUrl,dbUname,dbPassword);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return con;
	}
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
	
			String sname = request.getParameter("sname");
			String sroll = request.getParameter("sroll");
			String s1 = request.getParameter("s1");
			String s2 = request.getParameter("s2");
			String s3 = request.getParameter("s3");
			String s4 = request.getParameter("s4");
			
			Member member = new Member(sname,sroll,s1,s2,s3,s4);
				RegisterDao rDao=new RegisterDao();
				String result=rDao.insert(member);
				if(result=="DATA ENTERED!"){
					
					try {
						loadDriver(dbDriver);
						Connection con=getConnection();
						String sql="SELECT * FROM student2";
						PreparedStatement ps;
						ps = con.prepareStatement(sql);
						ResultSet rs=ps.executeQuery();
						String table="<table style='margin-left:auto;margin-right:auto;border:1px solid black;border-collapse:collapse;border-radius:10px;bgcolor:lightyellow'><tr>" + "<th style='border:2px solid black;padding:10px;'>StudentName</th>" + "<th style='border:2px solid black;padding:10px;'>StudentRoll</th>" + "<th style='border:2px solid black;padding:10px;'>1st Year CGPA</th>" + "<th style='border:2px solid black;padding:10px;'>2nd Year CGPA</th>" + "<th style='border:2px solid black;padding:10px;'>3rd Year CGPA</th>" + "<th style='border:2px solid black;padding:10px;'>4th Year CGPA</th>" ;
						while(rs.next()) {
							table+="<tr>"
									+ "<td style='border:2px solid black;padding:10px;'>" + rs.getString(1)+ "</td>" +
									"<td style='border:2px solid black;padding:10px;'>"+rs.getString(2)+"</td>"+
									"<td style='border:2px solid black;padding:10px;'>"+rs.getFloat(3)+"</td>"+
									"<td style='border:2px solid black;padding:10px;'>"+rs.getFloat(4)+"</td>"+
									"<td style='border:2px solid black;padding:10px;'>"+rs.getFloat(5)+"</td>"+
									"<td style='border:2px solid black;padding:10px;'>"+rs.getFloat(6)+"</td></tr>";
																			
						}
						table+="</table>";
						PrintWriter out =response.getWriter();
						out.write(table);
						out.print("<br>");
						out.print("<form action='Retrieve' method='post' style='text-align:center;padding:10px'>");
						out.print("<input type='submit' value='GET API DATA'>");
						out.print("</form>");
						out.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else {
					response.getWriter().print("Please Enter Data in Data Fields!");
				}
			
	}
}
